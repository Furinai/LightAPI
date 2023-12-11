package cn.linter.light.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangxiaoyang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    /**
     * 状态
     */
    private final String message;

    private final Throwable cause;

    public BusinessException(String message) {
        this.message = message;
        this.cause = null;
    }

    public BusinessException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

}