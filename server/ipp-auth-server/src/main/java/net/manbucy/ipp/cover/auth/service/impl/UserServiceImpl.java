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
import net.manbucy.ipp.cover.auth.mapper.UserMapper;
import net.manbucy.ipp.cover.auth.pojo.entity.User;
import net.manbucy.ipp.cover.auth.pojo.vo.RegInfoCheckResult;
import net.manbucy.ipp.cover.auth.pojo.vo.VerifyCodeSendResult;
import net.manbucy.ipp.cover.auth.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
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


    final UserMapper userMapper;
    final VerifyCodeLimitProperties verifyCodeLimitProperties;
    final RedisTemplate<String, String> template;
    final MailAccount mailAccount;

    @Override
    public RegInfoCheckResult checkUsername(String username) {
        if (NumberUtil.isNumber(username) || !ReUtil.isMatch(USERNAME_PATTERN, username)) {
            return RegInfoCheckResult.inputError("用户名格式错误");
        }

        if (isExistUsername(username)) {
            return RegInfoCheckResult.exist("该用户名已存在");
        }

        return RegInfoCheckResult.ok();
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
    public RegInfoCheckResult checkUserPhone(String phone) {
        if (!ReUtil.isMatch(PHONE_PATTERN, phone)) {
            return RegInfoCheckResult.inputError("手机号码格式错误");
        }
        if (isExistPhone(phone)) {
            return RegInfoCheckResult.exist("该手机号码已被使用");
        }
        return RegInfoCheckResult.ok();
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
    public RegInfoCheckResult checkUserEmail(String email) {
        if (!ReUtil.isMatch(EMAIL_PATTERN, email)) {
            return RegInfoCheckResult.inputError("电子邮箱格式错误");
        }
        if (isExistEmail(email)) {
            return RegInfoCheckResult.exist("该邮箱已被使用");
        }
        return RegInfoCheckResult.ok();
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
    public VerifyCodeSendResult sendRegVerifyCodeToPhone(String phone) {

        return VerifyCodeSendResult.ok();
    }

    @Override
    public VerifyCodeSendResult sendRegVerifyCodeToEmail(String email) {
        // 1、验证邮箱格式
        if (!ReUtil.isMatch(EMAIL_PATTERN, email)) {
            return VerifyCodeSendResult.inputError("电子邮箱格式错误");
        }

        final String regVerifyCodeEmailKey = REG_VERIFY_CODE_EMAIL_CACHE_PREFIX + email + ":";

        // 2、验证重复发送
        if (Boolean.TRUE.equals(template.hasKey(regVerifyCodeEmailKey + REG_VERIFY_CODE_REPEAT_SEND_KEY))) {
            return VerifyCodeSendResult.fail("重复发送，请"
                    + template.getExpire(regVerifyCodeEmailKey + REG_VERIFY_CODE_REPEAT_SEND_KEY, TimeUnit.SECONDS)
                    + "s后再试");
        }

        // 3、最大次数限制
        String contStr = template.opsForValue().get(regVerifyCodeEmailKey + REG_VERIFY_CODE_SEND_COUNT_KEY);
        if (StrUtil.isNotEmpty(contStr) &&
                NumberUtil.parseNumber(contStr).intValue() >= verifyCodeLimitProperties.getMaxLimitCount()) {
            return VerifyCodeSendResult.fail("操作太频繁，请"
                    + template.getExpire(regVerifyCodeEmailKey + REG_VERIFY_CODE_SEND_COUNT_KEY, TimeUnit.MINUTES)
                    + "min后再试");
        }

        // 4、生成验证码并发送
        String verifyCode = StrUtil.fill(String.valueOf(RandomUtil.randomInt(999999)), '0', 6, true);

        // 5、发送邮箱
        MailUtil.send(mailAccount, email,  "欢迎注册【子虚物优】科技",
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

        return VerifyCodeSendResult.ok();
    }
}
