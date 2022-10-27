package com.odeyalo.bot.suiri.support.lang;

import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class DefaultUserLocaleLanguageResolver implements UserLocaleLanguageResolver {
    private final UserRepository userRepository;

    @Autowired
    public DefaultUserLocaleLanguageResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String resolveLang(Update update) {
        org.telegram.telegrambots.meta.api.objects.User telegramUser = update.getMessage().getFrom();
        Long telegramId = telegramUser.getId();
        User user = userRepository.findUserByTelegramId(String.valueOf(telegramId));
        String telegramMessageLanguageCode = telegramUser.getLanguageCode();
        if (user == null) {
            return telegramMessageLanguageCode;
        }
        String preferredLanguageCode = user.getUserSettings().getLanguage();
        return preferredLanguageCode != null ? preferredLanguageCode : telegramMessageLanguageCode;
    }
}
