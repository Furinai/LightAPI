package cn.linter.light.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author wangxiaoyang
 */
public enum ParamType {

    /**
     * 参数类型
     */
    INT("整数"), FLOAT("浮点数"),
    STRING("字符串"), DATETIME("日期时间");

    private final String description;

    ParamType(String name) {
        this.description = name;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

}