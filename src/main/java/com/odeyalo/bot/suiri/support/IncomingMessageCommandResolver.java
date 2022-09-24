package com.odeyalo.bot.suiri.support;

public interface IncomingMessageCommandResolver {
    /**
     * Returns command name from given message
     * @param text - text to resolve from
     * @return - command name
     */
    String getCommandName(String text);
}
