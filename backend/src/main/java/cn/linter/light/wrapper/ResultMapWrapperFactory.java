package cn.linter.light.wrapper;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Map;

/**
 * @author wangxiaoyang
 */
public class ResultMapWrapperFactory implements ObjectWrapperFactory {

    @Override
    public boolean hasWrapperFor(Object object) {
        return object instanceof Map;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        return new ResultMapWrapper(metaObject, (Map<String, Object>) object);
    }

}