package com.odeyalo.bot.suiri.service.command.support.test;

import com.odeyalo.bot.suiri.entity.User;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Interface that test user knowledge
 */
public interface UserWordKnowledgeTestingManager {

    /**
     * Method to create and return the test for user.
     * If current manager does not support BotApiMethod return type will be returned null
     * @param update - incoming message update
     * @param user - user to create test
     * @return - user words knowledge test that contains in user dictionary
     */
    PartialBotApiMethod<?> getUserKnowledgeTest(Update update, User user);

}
