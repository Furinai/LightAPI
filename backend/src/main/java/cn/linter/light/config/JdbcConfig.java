package cn.linter.light.config;

import cn.linter.light.converter.BooleanToIntegerConverter;
import cn.linter.light.converter.IntegerToBooleanConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * Spring Data JDBC 配置
 *
 * @author wangxiaoyang
 */
@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {

    @Bean
    @Override
    @SuppressWarnings("NullableProblems")
    public JdbcCustomConversions jdbcCustomConversions() {
        List<Converter<?, ?>> converterList = Arrays.asList(
                new BooleanToIntegerConverter(),
                new IntegerToBooleanConverter()
        );
        return new JdbcCustomConversions(converterList);
    }

}