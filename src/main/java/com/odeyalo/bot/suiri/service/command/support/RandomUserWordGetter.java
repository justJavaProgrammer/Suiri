package com.odeyalo.bot.suiri.service.command.support;

import com.odeyalo.bot.suiri.entity.DictionaryItem;
import com.odeyalo.bot.suiri.entity.User;

/**
 * Support class to get random word from user's dictionary
 * @version 1.0.0
 */
public interface RandomUserWordGetter {
    /**
     * Returns a random word from user's dictionary
     * @param userId - user id to get dictionary
     * @return - random  word
     */
    DictionaryItem getRandomWord(String userId);

    /**
     * Returns a random word from user's dictionary
     * @param user - user with dictionary
     * @return - random  word
     */
    DictionaryItem getRandomWord(User user);

}
