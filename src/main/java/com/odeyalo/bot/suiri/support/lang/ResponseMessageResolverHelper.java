package com.odeyalo.bot.suiri.support.lang;

public interface ResponseMessageResolverHelper {

    /**
     * Return a message by property and translate it
     * @param langCode - language code, if language code is not supported will be returned message by english language code
     * @param property - message property
     * @return - message by property
     */
    default String getMessageByLanguageCode(String langCode, String property) {
        return getMessageByLanguageCode(langCode, property, Languages.ENGLISH);
    }

    /**
     * Return a message by property and translate it
     * @param langCode - language code, if language code is not supported will be returned default one
     * @param property - message property
     * @param defaultLanguageCode - default language code if langCode is not supported
     * @return - message by property
     */
    String getMessageByLanguageCode(String langCode, String property, String defaultLanguageCode);
}
