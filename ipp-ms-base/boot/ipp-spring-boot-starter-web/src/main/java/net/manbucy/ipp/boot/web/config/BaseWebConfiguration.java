package net.manbucy.ipp.boot.web.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ManBu
 */
@Configuration
@ComponentScan("net.manbucy.ipp.boot.web")
public class BaseWebConfiguration {

    @Bean
    Jackson2ObjectMapperBuilderCustomizer ippJackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class,
                    new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            jacksonObjectMapperBuilder.serializerByType(LocalDate.class,
                    new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            jacksonObjectMapperBuilder.serializerByType(LocalTime.class,
                    new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));

            jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class,
                    new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            jacksonObjectMapperBuilder.deserializerByType(LocalDate.class,
                    new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            jacksonObjectMapperBuilder.deserializerByType(LocalTime.class,
                    new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));

            jacksonObjectMapperBuilder.dateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));
            jacksonObjectMapperBuilder.visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            jacksonObjectMapperBuilder.visibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
            jacksonObjectMapperBuilder.visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
            jacksonObjectMapperBuilder.visibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
            jacksonObjectMapperBuilder.visibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.NONE);
            jacksonObjectMapperBuilder.visibility(PropertyAccessor.NONE, JsonAutoDetect.Visibility.NONE);
        };
    }
}
