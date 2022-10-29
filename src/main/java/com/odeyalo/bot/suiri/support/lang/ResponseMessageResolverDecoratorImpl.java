package com.odeyalo.bot.suiri.support.lang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
public class ResponseMessageResolverDecoratorImpl implements ResponseMessageResolverDecorator {
    private final LanguageResponseMessageResolverRegistry container;
    private final UserLocaleLanguageResolver languageResolver;

    @Autowired
    public ResponseMessageResolverDecoratorImpl(LanguageResponseMessageResolverRegistry container, UserLocaleLanguageResolver languageResolver) {
        this.container = container;
        this.languageResolver = languageResolver;
    }

    @Override
    public String getResponseMessage(Update update, String property) {
        String languageCode = languageResolver.resolveLang(update);
        LanguageResponseMessageResolverStrategy resolver = container.getOrDefault(languageCode, Languages.ENGLISH);
        return resolver.getMessage(property);
    }

    @Override
    public String getUserLanguage(Update update) {
        return languageResolver.resolveLang(update);
    }
}
