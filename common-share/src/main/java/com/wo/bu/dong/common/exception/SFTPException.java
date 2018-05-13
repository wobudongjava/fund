package com.wo.bu.dong.common.exception;

/**
 * SFTP异常
 */
public class SFTPException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SFTPException() {
        super();
    }

    public SFTPException(String message) {
        super(message);
    }

    public SFTPException(Throwable cause) {
        super(cause);
    }

    public SFTPException(String message, Throwable cause) {
        super(message, cause);
    }
}
