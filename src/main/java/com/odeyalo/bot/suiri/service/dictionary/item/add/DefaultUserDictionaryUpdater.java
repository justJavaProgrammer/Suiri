package com.odeyalo.bot.suiri.service.dictionary.item.add;

import com.odeyalo.bot.suiri.entity.Dictionary;
import com.odeyalo.bot.suiri.entity.DictionaryItem;
import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.repository.DictionaryRepository;
import com.odeyalo.bot.suiri.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserDictionaryUpdater implements UserDictionaryUpdater {
    private final UserRepository userRepository;
    private final DictionaryRepository dictionaryRepository;

    @Autowired
    public DefaultUserDictionaryUpdater(UserRepository userRepository, DictionaryRepository dictionaryRepository) {
        this.userRepository = userRepository;
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public void addWordToUser(String telegramId, DictionaryItem item) {
        User user = this.userRepository.findUserByTelegramId(telegramId);
        addWordToUser(user, item);
    }

    @Override
    public void addWordToUser(User user, DictionaryItem item) {
        Dictionary dictionary = user.getUserDictionary();

        dictionary.addToDictionary(item);
        dictionary.setUser(user);

        user.setUserDictionary(dictionary);
        item.setDictionary(dictionary);

        updateUserDictionary(user, dictionary);
    }

    @Override
    public void updateUserDictionary(User user, Dictionary dictionary) {
        this.userRepository.save(user);
        this.dictionaryRepository.save(dictionary);
    }
}
