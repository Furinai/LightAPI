package cn.linter.light.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * @author wangxiaoyang
 */
@WritingConverter
public class BooleanToIntegerConverter implements Converter<Boolean, Integer> {

    @Override
    public Integer convert(Boolean source) {
        return source.equals(Boolean.TRUE) ? 1 : 0;
    }

}