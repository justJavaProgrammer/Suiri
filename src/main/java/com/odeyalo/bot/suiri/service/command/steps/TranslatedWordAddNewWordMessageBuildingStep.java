package com.odeyalo.bot.suiri.service.command.steps;

import com.odeyalo.bot.suiri.domain.AddNewWordMessage;
import com.odeyalo.bot.suiri.service.command.support.state.AddNewWordState;
import com.odeyalo.bot.suiri.service.command.support.state.AddNewWordStateRepository;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;

import static com.odeyalo.bot.suiri.service.command.steps.AddNewWordLanguagePropertiesConstants.ADD_WORD_TRANSLATED_WORD_STEP_MESSAGE_PROPERTY;

@Component
public class TranslatedWordAddNewWordMessageBuildingStep extends AbstractAddNewWordMessageBuildingStep {
    private final Logger logger = LoggerFactory.getLogger(TranslatedWordAddNewWordMessageBuildingStep.class);

    @Autowired
    public TranslatedWordAddNewWordMessageBuildingStep(AddNewWordStateRepository stateRepository, ResponseMessageResolverDecorator responseMessageResolverDecorator) {
        super(stateRepository, responseMessageResolverDecorator);
    }

    @Override
    public BotApiMethod<?> processStep(Update update, AddNewWordMessage message) {
        String chatId = TelegramUtils.getChatId(update);
        AddNewWordState currentState = getCurrentState(chatId);
        if (currentState != getState()) {
            this.logger.warn("The step was skipped since state was wrong. Expected: {}, received: {}",  currentState, getState());
            return null;
        }
        String text = TelegramUtils.getText(update);
        this.logger.info("Received translated word: {}", text);
        message.setTranslatedWords(Collections.singletonList(text));
        String responseMessage = responseMessageResolverDecorator.getResponseMessage(update, ADD_WORD_TRANSLATED_WORD_STEP_MESSAGE_PROPERTY);
        return new SendMessage(chatId, responseMessage);
    }

    @Override
    public AddNewWordState getState() {
        return AddNewWordState.TRANSLATED_WORD;
    }
}
