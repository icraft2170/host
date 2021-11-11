package com.host.host.exception;

public class DuplicateHostException extends RuntimeException{
    public DuplicateHostException() {
        super();
    }

    public DuplicateHostException(String message) {
        super(message);
    }

    public DuplicateHostException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateHostException(Throwable cause) {
        super(cause);
    }

    protected DuplicateHostException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
