package com.host.host.exception;

public class HostException extends RuntimeException {
    public HostException() {
        super();
    }

    public HostException(String message) {
        super(message);
    }

    public HostException(String message, Throwable cause) {
        super(message, cause);
    }

    public HostException(Throwable cause) {
        super(cause);
    }

    protected HostException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
