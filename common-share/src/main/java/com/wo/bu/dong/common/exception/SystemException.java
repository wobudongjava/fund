package com.wo.bu.dong.common.exception;

/**
 * 系统异常
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

}
