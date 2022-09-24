package com.odeyalo.bot.suiri.exception;

/**
 * Exception that can be thrown when command was not found.
 */
public class CommandNotFoundException extends RuntimeException {
    private final String userMessage;


    public CommandNotFoundException(String userMessage) {
        super(userMessage);
        this.userMessage = userMessage;
    }

    /**
     *
     * @param userMessage - message to show to the user
     * @param message - logs
     */
    public CommandNotFoundException(String userMessage, String message) {
        super(message);
        this.userMessage = userMessage;
    }

    /**
     *
     * @param userMessage - message to show to the user
     * @param message - logs
     * @param cause - original exception
     */
    public CommandNotFoundException(String userMessage, String message, Throwable cause) {
        super(message, cause);
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
