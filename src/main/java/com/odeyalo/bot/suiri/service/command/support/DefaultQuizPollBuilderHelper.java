package com.odeyalo.bot.suiri.service.command.support;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DefaultQuizPollBuilderHelper implements QuizPollBuilderHelper {
    private static final String QUIZ = "quiz";

    @Override
    public SendPoll createPoll(String question, String chatId, String explanation, String answer, SortedSet<String> options, int limit) {
        SendPoll.SendPollBuilder builder = SendPoll.builder()
                .type(QUIZ)
                .question(question)
                .explanation(explanation)
                .chatId(chatId);

        List<String> optionsList = new ArrayList<>(options);

        int size = optionsList.size();
        Set<String> optionsResult;
        int answerId;
        if (limit >= size) {
            answerId = ThreadLocalRandom.current().nextInt(0, size);
            optionsResult = buildQuiz(answer, optionsList, answerId, size);
        } else {
            answerId = ThreadLocalRandom.current().nextInt(0, limit);
            optionsResult = buildQuiz(answer, optionsList, answerId, limit);

        }

        builder.options(optionsResult)
                .correctOptionId(answerId);

        return builder.build();
    }

    private Set<String> buildQuiz(String answer, List<String> optionsList, int answerId, int limit) {
        List<String> optionsSubList = optionsList.subList(0, limit);
        optionsSubList.remove(answer);
        Collections.shuffle(optionsSubList);
        optionsSubList.add(answerId, answer);
        return new LinkedHashSet<>(optionsSubList);
    }
}
