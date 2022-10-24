package com.odeyalo.bot.suiri.service.command.support;

import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;

import java.util.SortedSet;

public interface QuizPollBuilderHelper {

    Integer DEFAULT_LIMIT = 4;

    /**
     * Create quiz poll
     * @param answer - answer to quiz
     * @param options - options with other words
     * @param limit - limit of number of options(without answer)
     */
    SendPoll createPoll(String question, String chatId, String explanation, String answer, SortedSet<String> options, int limit);
}
