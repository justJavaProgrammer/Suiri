package com.odeyalo.bot.suiri.service.command.support;

import com.odeyalo.bot.suiri.entity.DictionaryItem;

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

}
