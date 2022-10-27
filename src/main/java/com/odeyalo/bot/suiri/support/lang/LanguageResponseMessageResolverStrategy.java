package com.odeyalo.bot.suiri.support.lang;

/**
 * Support class to resolve message from files by different language
 */
public interface LanguageResponseMessageResolverStrategy {

    String getMessage(String property);

    /**
     * Language that this strategy is support
     * @return - language code of this strategy
     */
    String languageCode();

}
