package cn.linter.light.config;

import cn.linter.light.converter.BooleanToIntegerConverter;
import cn.linter.light.converter.IntegerToBooleanConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.List;

/**
 * Spring Data JDBC 配置
 *
 * @author wangxiaoyang
 */
@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {

    @NonNull
    @Override
    protected List<?> userConverters() {
        return Arrays.<Converter<?, ?>>asList(
                new BooleanToIntegerConverter(),
                new IntegerToBooleanConverter()
        );
    }

}