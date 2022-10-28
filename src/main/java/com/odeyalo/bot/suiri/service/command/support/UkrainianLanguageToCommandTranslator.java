package com.odeyalo.bot.suiri.service.command.support;

import com.odeyalo.bot.suiri.support.lang.Languages;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UkrainianLanguageToCommandTranslator implements LanguageToCommandTranslator {
    private final Map<String, String> words = new HashMap<>();

    {
        words.put("Мова", "Language");
    }

    @Override
    public String translateTo(String originalText) {
        return words.get(originalText);
    }

    @Override
    public String supportedLanguage() {
        return Languages.UKRAINIAN;
    }
}
