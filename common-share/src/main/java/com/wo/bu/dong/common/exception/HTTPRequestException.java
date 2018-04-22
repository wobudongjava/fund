package com.wo.bu.dong.common.exception;

/**
 * DAO层异常
 */
public class HTTPRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HTTPRequestException() {
        super();
    }

    public HTTPRequestException(String message) {
        super(message);
    }

    public HTTPRequestException(Throwable cause) {
        super(cause);
    }

    public HTTPRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
