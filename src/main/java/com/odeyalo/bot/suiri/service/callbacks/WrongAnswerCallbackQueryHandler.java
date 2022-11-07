package com.odeyalo.bot.suiri.service.callbacks;

import com.odeyalo.bot.suiri.service.command.support.test.Questions;
import com.odeyalo.bot.suiri.service.command.support.test.store.UserKnowledgeTestingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


/**
 * Handle wrong answer to question
 */
@Service
public class WrongAnswerCallbackQueryHandler implements CallbackQueryHandler {
    private final String CALLBACK_NAME = "failed";
    private final UserKnowledgeTestingStore store;

    @Autowired
    public WrongAnswerCallbackQueryHandler(UserKnowledgeTestingStore store) {
        this.store = store;
    }

    @Override
    public BotApiMethod<?> handle(CallbackQuery query) {
        String chatId = String.valueOf(query.getMessage().getChatId());
        Questions questions = store.getById(chatId);
        Questions.CurrentQuestion currentQuestion = questions.getCurrentQuestion();
        currentQuestion.setAnswered(true);
        this.store.save(chatId, questions);
        return new SendMessage(chatId, currentQuestion.getCurrentQuestion().getExplanation());
    }

    @Override
    public String getCallbackDataName() {
        return CALLBACK_NAME;
    }
}
