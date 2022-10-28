package com.odeyalo.bot.suiri.service.command.support;

import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.UserLocaleLanguageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MultiLanguageCommandTranslatorDelegateImpl implements MultiLanguageCommandTranslatorDelegate {
    private final Map<String, LanguageToCommandTranslator> map;
    private final UserLocaleLanguageResolver languageResolver;

    @Autowired
    public MultiLanguageCommandTranslatorDelegateImpl(List<LanguageToCommandTranslator> translators, UserLocaleLanguageResolver languageResolver) {
        this.map = translators.stream().collect(Collectors.toMap(LanguageToCommandTranslator::supportedLanguage, Function.identity()));
        this.languageResolver = languageResolver;
    }
    @Override
    public String getOriginalCommand(Update update) {
        String langCode = this.languageResolver.resolveLang(update);
        String text = TelegramUtils.getText(update);
        return map.getOrDefault(langCode, new LanguageToCommandTranslator() {
            @Override
            public String translateTo(String originalText) {
                return originalText;
            }

            @Override
            public String supportedLanguage() {
                return "en";
            }
        }).translateTo(text);
    }
}
