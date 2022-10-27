package com.odeyalo.bot.suiri.support.lang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * LanguageResponseMessageResolverStrategy implementation that returns only ukrainian response messages
 */

@Service
public class UkrainianLanguageResponseMessageResolverStrategy extends AbstractPropertySourceLanguageResponseMessageResolverStrategy {

    @Autowired
    public UkrainianLanguageResponseMessageResolverStrategy(@Value("${dictionary.ukrainian.path}") String dictionaryPath) throws IOException {
        super(dictionaryPath);
    }

    @Override
    public String getMessage(String property) {
        return this.doGetProperty(property);
    }

    @Override
    public String languageCode() {
        return Languages.UKRAINIAN;
    }
}
