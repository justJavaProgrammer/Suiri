package com.odeyalo.bot.suiri.support.lang;

public interface LanguageResponseMessageResolverRegistry {

    void registry(String languageCode, LanguageResponseMessageResolverStrategy resolver);

    LanguageResponseMessageResolverStrategy getResolver(String languageCode);

    LanguageResponseMessageResolverStrategy getOrDefault(String languageCode, String defaultLanguage);

    LanguageResponseMessageResolverStrategy getOrDefault(String languageCode, LanguageResponseMessageResolverStrategy resolver);

    boolean containsLanguageCode(String langCode);

    void delete(String langCode);
}
