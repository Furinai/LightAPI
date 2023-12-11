package cn.linter.light.handler;

import cn.linter.light.exception.ApiNotEnableException;
import cn.linter.light.exception.ApiNotFoundException;
import cn.linter.light.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

/**
 * @author wangxiaoyang
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<Object> handleMethodNotAllowedException() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @ExceptionHandler(ApiNotEnableException.class)
    public ResponseEntity<Object> handleNotEnableException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("接口被禁用");
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleException(BusinessException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}