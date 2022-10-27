package com.odeyalo.bot.suiri.support.lang;

import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.entity.UserSettings;
import com.odeyalo.bot.suiri.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleUserLanguageChanger implements UserLanguageChanger {
    private final UserRepository userRepository;

    @Autowired
    public SimpleUserLanguageChanger(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void changeUserLanguage(User user, String languageCode) {
        UserSettings userSettings = user.getUserSettings();
        userSettings.setLanguage(languageCode);
        this.userRepository.save(user);
    }
}
