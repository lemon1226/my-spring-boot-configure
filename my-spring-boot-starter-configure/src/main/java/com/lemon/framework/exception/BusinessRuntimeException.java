package com.lemon.framework.exception;

/**
 * description: 业务异常类
 *
 * @author lemon
 * @date 2019-06-25 16:18:06 创建
 */
public class BusinessRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -7464087034363911027L;

    public BusinessRuntimeException() {
        super();
    }

    public BusinessRuntimeException(String message) {
        super(message);
    }

    public BusinessRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessRuntimeException(Throwable cause) {
        super(cause);
    }
}