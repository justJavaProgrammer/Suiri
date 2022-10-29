package com.odeyalo.bot.suiri.support.lang;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Decorator that resolve user's language and return message
 */
public interface ResponseMessageResolverDecorator {
    /**
     * Return response message by language code
     * @param update - telegram update
     * @param property - property that will be returned
     * @return - response message
     */
    String getResponseMessage(Update update, String property);

    String getUserLanguage(Update update);
}
