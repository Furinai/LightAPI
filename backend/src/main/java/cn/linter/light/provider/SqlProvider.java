package cn.linter.light.provider;

import cn.linter.light.entity.ParamMap;

/**
 * @author wangxiaoyang
 */
public class SqlProvider {

    public String provideSql(ParamMap param) {
        return "<script>" + param.getSqlStatement() + "</script>";
    }

}