package com.odeyalo.bot.suiri.support.lang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Decorator that resolve user's language and return message
 * @see com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator
 */
@Service
public class ResponseMessageResolverDecoratorImpl implements ResponseMessageResolverDecorator {
    private final ResponseMessageResolverHelper responseMessageResolverHelper;
    private final UserLocaleLanguageResolver languageResolver;

    @Autowired
    public ResponseMessageResolverDecoratorImpl(ResponseMessageResolverHelper responseMessageResolverHelper, UserLocaleLanguageResolver languageResolver) {
        this.responseMessageResolverHelper = responseMessageResolverHelper;
        this.languageResolver = languageResolver;
    }

    @Override
    public String getResponseMessage(Update update, String property) {
        String languageCode = languageResolver.resolveLang(update);
        return responseMessageResolverHelper.getMessageByLanguageCode(languageCode, property);
    }

    @Override
    public String getUserLanguage(Update update) {
        return languageResolver.resolveLang(update);
    }
}
