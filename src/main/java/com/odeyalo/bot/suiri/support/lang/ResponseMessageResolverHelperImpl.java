package com.odeyalo.bot.suiri.support.lang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponseMessageResolverHelperImpl implements ResponseMessageResolverHelper {
    private final LanguageResponseMessageResolverRegistry container;

    @Autowired
    public ResponseMessageResolverHelperImpl(LanguageResponseMessageResolverRegistry container) {
        this.container = container;
    }

    @Override
    public String getMessageByLanguageCode(String langCode, String property, String defaultLangCode) {
        LanguageResponseMessageResolverStrategy resolver = container.getOrDefault(langCode, defaultLangCode);
        return resolver.getMessage(property);
    }
}
