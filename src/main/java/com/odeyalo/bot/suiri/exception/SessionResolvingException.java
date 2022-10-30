package com.odeyalo.bot.suiri.exception;

/**
 * Exception that occurred when session resolving from remote service has been failed
 */
public class SessionResolvingException extends RuntimeException {
    public SessionResolvingException(String message) {
        super(message);
    }

    public SessionResolvingException(String message, Throwable cause) {
        super(message, cause);
    }
}
