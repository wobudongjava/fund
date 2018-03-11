package com.wo.bu.dong.common.exception;

/**
 * zip解压宿异常
 */
public class ZipException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ZipException() {
        super();
    }

    public ZipException(String message) {
        super(message);
    }

    public ZipException(Throwable cause) {
        super(cause);
    }

    public ZipException(String message, Throwable cause) {
        super(message, cause);
    }
}
