package com.odeyalo.bot.suiri.service.command.support.test;

import com.odeyalo.bot.suiri.entity.User;

import java.util.List;

public interface MultiQuestionsUserDictionaryKnowledgeGenerator {

    /**
     * Generate list of questions for user
     * @param user - user
     * @param size - size of generated list with questions
     * @return - list of questions
     */
    List<UserDictionaryKnowledgeTest> generateTests(User user, Integer size);
}
