package com.odeyalo.bot.suiri.support;

import org.springframework.stereotype.Component;

@Component
public class PrefixIncomingMessageCommandResolver implements IncomingMessageCommandResolver {
    public static final String PREFIX = "/";

    /**
     * @param text - text to resolve from
     * @return - null if text not started with '/', otherwise command
     */
    @Override
    public String getCommandName(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        if (!text.startsWith(PREFIX)) {
            return text;
        }
        return text.split(" ")[0];
    }
}
