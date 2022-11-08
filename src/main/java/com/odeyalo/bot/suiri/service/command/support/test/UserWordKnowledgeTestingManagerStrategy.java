package com.odeyalo.bot.suiri.service.command.support.test;

import com.odeyalo.bot.suiri.entity.User;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Strategy to handle creation user knowledge test
 * This interface simplify the calls to UserWordKnowledgeTestingManager implementation and simply delegate handling to UserWordKnowledgeTestingManager implementation
 * @see UserWordKnowledgeTestingManager
 * @see AbstractUserWordKnowledgeTestingManager
 */
public interface UserWordKnowledgeTestingManagerStrategy {

    /**
     * Method to create and return the test for user
     * @param update - telegram update
     * @param user - user to create test
     * @return - test for this user
     */
    PartialBotApiMethod<?> getUserKnowledgeTest(Update update, User user);

}
