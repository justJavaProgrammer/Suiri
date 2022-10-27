package com.odeyalo.bot.suiri.support.lang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EnglishLanguageResponseMessageResolverStrategy extends AbstractPropertySourceLanguageResponseMessageResolverStrategy {

    @Autowired
    public EnglishLanguageResponseMessageResolverStrategy(@Value("${dictionary.english.path}") String dictionaryPath) throws IOException {
        super(dictionaryPath);
    }

    @Override
    public String getMessage(String property) {
        return this.doGetProperty(property);
    }

    @Override
    public String languageCode() {
        return Languages.ENGLISH;
    }
}
