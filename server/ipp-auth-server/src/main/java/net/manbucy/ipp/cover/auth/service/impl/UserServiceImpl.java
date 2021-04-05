package net.manbucy.ipp.cover.auth.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.manbucy.ipp.cover.auth.config.properties.VerifyCodeLimitProperties;
import net.manbucy.ipp.cover.auth.controller.user.ao.RegInfo;
import net.manbucy.ipp.cover.auth.mapper.UserMapper;
import net.manbucy.ipp.cover.auth.pojo.dto.RegistrationError;
import net.manbucy.ipp.cover.auth.pojo.dto.RegistrationResult;
import net.manbucy.ipp.cover.auth.pojo.entity.User;
import net.manbucy.ipp.cover.auth.pojo.enums.AuthErrorCode;
import net.manbucy.ipp.cover.auth.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author ManBu
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    static final String REG_VERIFY_CODE_EMAIL_CACHE_PREFIX = "reg_verify_code:email:";
    static final String REG_VERIFY_CODE_REPEAT_SEND_KEY = "repeat_send";
    static final String REG_VERIFY_CODE_SEND_COUNT_KEY = "send_count";
    static final String REG_VERIFY_CODE_KEY = "verify_code";

    final Pattern USERNAME_PATTERN = Pattern.compile("[A-Za-z0-9_]{1,16}");

    final Pattern PHONE_PATTERN = Pattern.compile("0?(13|14|15|17|18|19)[0-9]{9}");

    final Pattern EMAIL_PATTERN = Pattern.compile("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}");

    final Pattern PASSWORD_PATTERN = Pattern.compile("(\\S){8,16}");

    final Pattern WRONG_PASSWORD_PATTERN = Pattern.compile("[^\\x00-\\xff]*");

    final Pattern VERIFY_CODE_PATTERN = Pattern.compile("\\d{6}");


    final UserMapper userMapper;
    final VerifyCodeLimitProperties verifyCodeLimitProperties;
    final RedisTemplate<String, String> template;
    final MailAccount mailAccount;

    @Override
    public RegistrationResult checkUsername(String username) {
        if (StrUtil.isBlank(username)) {
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_USERNAME, AuthErrorCode.USERNAME_CHECK_ERROR)
                            .inputError("用户名不能为空"));
        }

        if (NumberUtil.isNumber(username) || !ReUtil.isMatch(USERNAME_PATTERN, username)) {
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_USERNAME, AuthErrorCode.USERNAME_CHECK_ERROR)
                            .inputError("用户名格式错误"));
        }

        if (isExistUsername(username)) {
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_USERNAME, AuthErrorCode.USERNAME_EXIST_ERROR)
                            .exist("用户名已存在"));
        }

        return RegistrationResult.ok();
    }

    /**
     * 判断用户名是否已存在
     *
     * @param username 用户名
     * @return 是否已存在
     */
    private boolean isExistUsername(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User user = userMapper.selectOne(userQueryWrapper);
        return user != null;
    }

    @Override
    public RegistrationResult checkUserPhone(String phone) {
        RegistrationResult formatCheckResult = checkPhoneFormat(phone);
        if (!formatCheckResult.isSuccess()) {
            return formatCheckResult;
        }

        if (isExistPhone(phone)) {
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_PHONE, AuthErrorCode.PHONE_EXIST_ERROR)
                            .inputError("该手机号码已被使用"));
        }
        return RegistrationResult.ok();
    }

    /**
     * 校验手机号码格式
     *
     * @param phone 待校验的手机号码
     * @return 校验结果
     */
    private RegistrationResult checkPhoneFormat(String phone) {
        if (StrUtil.isBlank(phone)) {
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_PHONE, AuthErrorCode.PHONE_CHECK_ERROR)
                            .inputError("手机号码不能为空"));
        }

        if (!ReUtil.isMatch(PHONE_PATTERN, phone)) {
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_PHONE, AuthErrorCode.PHONE_CHECK_ERROR)
                            .inputError("手机号码格式错误"));
        }
        return RegistrationResult.ok();
    }

    /**
     * 判断手机号码是否已存在
     *
     * @param phone 手机号码
     * @return 是否已存在
     */
    private boolean isExistPhone(String phone) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("phone", phone);
        User user = userMapper.selectOne(userQueryWrapper);
        return user != null;
    }


    @Override
    public RegistrationResult checkUserEmail(String email) {
        RegistrationResult formatCheckResult = checkEmailFormat(email);
        if (!formatCheckResult.isSuccess()) {
            return formatCheckResult;
        }

        if (isExistEmail(email)) {
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_EMAIL, AuthErrorCode.EMAIL_EXIST_ERROR)
                            .inputError("该电子邮箱已被使用"));
        }

        return RegistrationResult.ok();
    }

    /**
     * 校验电子邮箱格式
     *
     * @param email 待校验的邮箱
     * @return 校验结果
     */
    private RegistrationResult checkEmailFormat(String email) {
        if (StrUtil.isBlank(email)) {
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_EMAIL, AuthErrorCode.EMAIL_CHECK_ERROR)
                            .inputError("电子邮箱不能为空"));
        }
        if (!ReUtil.isMatch(EMAIL_PATTERN, email)) {
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_EMAIL, AuthErrorCode.EMAIL_CHECK_ERROR)
                            .inputError("电子邮箱格式错误"));
        }

        return RegistrationResult.ok();
    }

    /**
     * 判断邮箱是否已存在
     *
     * @param email 邮箱
     * @return 是否已存在
     */
    private boolean isExistEmail(String email) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", email);
        User user = userMapper.selectOne(userQueryWrapper);
        return user != null;
    }


    @Override
    public RegistrationResult sendRegVerifyCodeToPhone(String phone) {

        return RegistrationResult.ok();
    }

    @Override
    public RegistrationResult sendRegVerifyCodeToEmail(String email) {
        RegistrationResult formatCheckResult = checkEmailFormat(email);
        if (!formatCheckResult.isSuccess()) {
            return formatCheckResult;
        }

        final String regVerifyCodeEmailKey = REG_VERIFY_CODE_EMAIL_CACHE_PREFIX + email + ":";

        // 2、验证重复发送
        if (Boolean.TRUE.equals(template.hasKey(regVerifyCodeEmailKey + REG_VERIFY_CODE_REPEAT_SEND_KEY))) {
            Long expireTime = template.getExpire(regVerifyCodeEmailKey + REG_VERIFY_CODE_REPEAT_SEND_KEY, TimeUnit.SECONDS);
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_VERIFY_CODE, AuthErrorCode.VERIFY_CODE_SEND_ERROR)
                            .operateError("重复发送，请" + expireTime + "s后再试")
                            .lockOperate(expireTime)
                    );
        }

        // 3、最大次数限制
        String contStr = template.opsForValue().get(regVerifyCodeEmailKey + REG_VERIFY_CODE_SEND_COUNT_KEY);
        if (StrUtil.isNotEmpty(contStr) &&
                NumberUtil.parseNumber(contStr).intValue() >= verifyCodeLimitProperties.getMaxLimitCount()) {
            Long expireTime = template.getExpire(regVerifyCodeEmailKey + REG_VERIFY_CODE_SEND_COUNT_KEY, TimeUnit.MINUTES);
            Long expireTimeSecond = expireTime != null ? expireTime * 60 : null;
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_VERIFY_CODE, AuthErrorCode.VERIFY_CODE_SEND_ERROR)
                            .operateError("操作太频繁，请" + expireTime + "min后再试")
                            .lockOperate(expireTimeSecond)
                    );
        }

        // 4、生成验证码并发送
        String verifyCode = StrUtil.fill(String.valueOf(RandomUtil.randomInt(999999)), '0', 6, true);

        // 5、发送邮箱
        MailUtil.send(mailAccount, email, "欢迎注册【子虚物优】科技",
                String.format("您的验证码为：%s 。请不要将验证码告诉给其他人，验证码%s分钟内有效", verifyCode,
                        verifyCodeLimitProperties.getCodeExpireTime() / 60), false);


        // 6、存储验证码
        template.opsForValue().set(regVerifyCodeEmailKey + REG_VERIFY_CODE_KEY, verifyCode,
                verifyCodeLimitProperties.getCodeExpireTime(), TimeUnit.SECONDS);
        // 设置 防重复发送标记
        template.opsForValue().set(regVerifyCodeEmailKey + REG_VERIFY_CODE_REPEAT_SEND_KEY, "1",
                verifyCodeLimitProperties.getRepeatLimitTime(), TimeUnit.SECONDS);

        // 设置 防多次发送 初始数据
        template.opsForValue().setIfAbsent(regVerifyCodeEmailKey + REG_VERIFY_CODE_SEND_COUNT_KEY, "0",
                verifyCodeLimitProperties.getMaxLimitTime(), TimeUnit.SECONDS);
        // 发送次数+1
        template.opsForValue().increment(regVerifyCodeEmailKey + REG_VERIFY_CODE_SEND_COUNT_KEY);

        return RegistrationResult.ok();
    }

    @Override
    public RegistrationResult registrationByPhone(RegInfo regInfo) {
        RegistrationResult commonCheckResult = registrationCommonCheck(regInfo);
        if (!commonCheckResult.isSuccess()) {
            return commonCheckResult;
        }
        // 校验手机号码
        RegistrationResult phoneCheckResult = checkUserPhone(regInfo.getPhone());
        if (!phoneCheckResult.isSuccess()) {
            return phoneCheckResult;
        }
        return null;
    }

    @Override
    public RegistrationResult registrationByEmail(RegInfo regInfo) {
        RegistrationResult commonCheck = registrationCommonCheck(regInfo);
        if (!commonCheck.isSuccess()) {
            return commonCheck;
        }

        // 校验电子邮箱
        RegistrationResult emailCheckResult = checkUserEmail(regInfo.getEmail());
        if (!emailCheckResult.isSuccess()) {
            return emailCheckResult;
        }

        // 验证 验证码
        final String regVerifyCodeEmailKey = REG_VERIFY_CODE_EMAIL_CACHE_PREFIX + regInfo.getEmail() + ":";
        String verifyCode = template.opsForValue().getAndSet(regVerifyCodeEmailKey + REG_VERIFY_CODE_KEY, "");
        template.delete(regVerifyCodeEmailKey + REG_VERIFY_CODE_KEY);
        if (!regInfo.getVerifyCode().equals(verifyCode)) {
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_VERIFY_CODE, AuthErrorCode.VERIFY_CODE_CHECK_ERROR)
                            .inputError("验证码不正确"));
        }

        // 生成用户信息
        return saveRegistrationUserInfo(regInfo);
    }

    /**
     * 注册用户时的公共校验
     * 校验用户名、密码是否符合、验证码是否符合格式
     *
     * @param regInfo 注册信息
     * @return 校验结果
     */
    private RegistrationResult registrationCommonCheck(RegInfo regInfo) {
        RegistrationResult usernameCheckResult = checkUsername(regInfo.getUsername());
        if (!usernameCheckResult.isSuccess()) {
            return usernameCheckResult;
        }

        if (StrUtil.isBlank(regInfo.getPassword())) {
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_PASSWORD, AuthErrorCode.PASSWORD_CHECK_ERROR)
                            .inputError("密码不能为空"));
        }
        if (ReUtil.isMatch(WRONG_PASSWORD_PATTERN, regInfo.getPassword()) || !ReUtil.isMatch(PASSWORD_PATTERN, regInfo.getPassword())) {
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_PASSWORD, AuthErrorCode.PASSWORD_CHECK_ERROR)
                            .inputError("密码格式不正确"));
        }

        if (StrUtil.isBlank(regInfo.getVerifyCode())) {
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_VERIFY_CODE, AuthErrorCode.VERIFY_CODE_CHECK_ERROR)
                            .inputError("验证码不能为空"));
        }
        if (!ReUtil.isMatch(VERIFY_CODE_PATTERN, regInfo.getVerifyCode())) {
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_VERIFY_CODE, AuthErrorCode.VERIFY_CODE_CHECK_ERROR)
                            .inputError("验证码式不正确"));
        }

        return RegistrationResult.ok();
    }

    /**
     * 保存注册用户信息
     *
     * @param regInfo 用户信息
     * @return 结果
     */
    private RegistrationResult saveRegistrationUserInfo(RegInfo regInfo) {
        User user = new User();
        user.setUsername(regInfo.getUsername());
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(regInfo.getPassword()));
        user.setPhone(regInfo.getPhone());
        user.setEmail(regInfo.getEmail());
        user.setLocked(false);

        try{
            int rows = userMapper.insert(user);
            if (rows != 1) {
                return RegistrationResult.fail()
                        .addError(new RegistrationError(RegistrationError.TYPE_OTHER, AuthErrorCode.USER_REGISTRATION_ERROR)
                                .operateError("创建用户失败"));
            }
        } catch (Exception e){
            return RegistrationResult.fail()
                    .addError(new RegistrationError(RegistrationError.TYPE_OTHER, AuthErrorCode.USER_REGISTRATION_ERROR)
                            .operateError("创建用户失败"));
        }

        return RegistrationResult.ok();
    }
}
