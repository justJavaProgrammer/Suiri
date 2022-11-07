package com.odeyalo.bot.suiri.service.command.support.test.store;

import com.odeyalo.bot.suiri.service.command.support.test.Questions;

/**
 * Store correct and wrong answers to questions. It also supports to store result of test
 */
public interface UserKnowledgeTestingStore {

    void save(String id, Questions questions);

    Questions getById(String id);

    void remove(String id);
}
