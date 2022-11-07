package com.odeyalo.bot.suiri.service.command.support.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Contains questions, question's size and user's score of given correct answers
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Questions {
    private List<UserDictionaryKnowledgeTest> questions;
    private CurrentQuestion currentQuestion;
    private Integer questionsSize;
    private int correctAnswersScore;

    public Questions(List<UserDictionaryKnowledgeTest> questions) {
        this.questions = questions;
        this.questionsSize = questions.size();
    }

    /**
     * Take and remove the first question from list
     */
    public UserDictionaryKnowledgeTest poll() {
        if (questions.size() != 0) {
            UserDictionaryKnowledgeTest test = questions.get(0);
            questions.remove(0);
            currentQuestion = new CurrentQuestion(test, false);
            return test;
        }
        return null;
    }

    public void incrementCorrectAnswers() {
        if (!currentQuestion.isAnswered) {
            correctAnswersScore++;
        }
    }


    @Data
    @AllArgsConstructor
    public static class CurrentQuestion {
        private UserDictionaryKnowledgeTest currentQuestion;
        // True if user already give an answer to this question
        private boolean isAnswered;
    }
}
