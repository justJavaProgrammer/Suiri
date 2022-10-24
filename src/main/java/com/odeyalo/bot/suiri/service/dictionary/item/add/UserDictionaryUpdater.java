package com.odeyalo.bot.suiri.service.dictionary.item.add;

import com.odeyalo.bot.suiri.entity.Dictionary;
import com.odeyalo.bot.suiri.entity.DictionaryItem;
import com.odeyalo.bot.suiri.entity.User;

public interface UserDictionaryUpdater {

    void addWordToUser(String telegramId, DictionaryItem item);

    void addWordToUser(User user, DictionaryItem item);

    void updateUserDictionary(User user, Dictionary dictionary);
}
