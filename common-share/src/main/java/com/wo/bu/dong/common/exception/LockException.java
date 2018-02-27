package com.wo.bu.dong.common.exception;

/**
 * 加锁或解锁异常
 */
public class LockException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LockException() {
        super();
    }

    public LockException(String message) {
        super(message);
    }

    public LockException(Throwable cause) {
        super(cause);
    }

    public LockException(String message, Throwable cause) {
        super(message, cause);
    }
}
