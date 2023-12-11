package cn.linter.light.wrapper;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;

import java.util.Arrays;
import java.util.Map;

/**
 * @author wangxiaoyang
 */
public class ResultMapWrapper extends MapWrapper {

    public ResultMapWrapper(MetaObject metaObject, Map<String, Object> map) {
        super(metaObject, map);
    }

    private static String mapUnderscoreToCamelCase(String underscoreName) {
        char[] charList = underscoreName.toCharArray();
        int length = charList.length;
        for (int i = 0; i < length; ++i) {
            if (charList[i] == '_') {
                length--;
                System.arraycopy(charList, i + 1, charList, i, length - i);
                if (charList[i] >= 97 && charList[i] <= 122) {
                    charList[i] -= 32;
                }
            }
        }
        if (length == underscoreName.length()) {
            return underscoreName;
        }
        return String.valueOf(Arrays.copyOf(charList, length));
    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        if (useCamelCaseMapping) {
            return mapUnderscoreToCamelCase(name);
        }
        return name;
    }

}