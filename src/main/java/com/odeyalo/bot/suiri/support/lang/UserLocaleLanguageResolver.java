package com.odeyalo.bot.suiri.support.lang;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Support class to resolve user's locale language and return the result
 *
 */
public interface UserLocaleLanguageResolver {
    /**
     * Resolve user's language
     * @param update - update by telegram
     * @return - user's language preference
     */
    String resolveLang(Update update);

}
