package com.odeyalo.bot.suiri.service.command.support.test;

import com.odeyalo.bot.suiri.entity.User;

/**
 * Generate the test for user to test user's knowledge
 */
public interface UserDictionaryKnowledgeTestGenerator {

    /**
     * Generate test for user
     * @param user - user for test
     * @return - UserDictionaryKnowledgeTest
     */
    UserDictionaryKnowledgeTest generateTest(User user);
}
