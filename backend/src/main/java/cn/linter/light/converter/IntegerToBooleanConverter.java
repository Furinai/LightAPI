package cn.linter.light.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * @author wangxiaoyang
 */
@ReadingConverter
public class IntegerToBooleanConverter implements Converter<Integer, Boolean> {

    @Override
    public Boolean convert(Integer source) {
        return source.equals(1) ? Boolean.TRUE : Boolean.FALSE;
    }

}