package com.odeyalo.bot.suiri.support.lang;

import com.odeyalo.bot.suiri.entity.User;

/**
 * Class to change user's preferred language
 */
public interface UserLanguageChanger {

    void changeUserLanguage(User user, String languageCode);

}
