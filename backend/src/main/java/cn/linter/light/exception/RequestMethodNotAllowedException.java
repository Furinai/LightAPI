package cn.linter.light.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangxiaoyang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RequestMethodNotAllowedException extends RuntimeException {
}