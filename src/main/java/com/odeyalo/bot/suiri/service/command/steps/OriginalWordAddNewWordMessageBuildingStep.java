package com.odeyalo.bot.suiri.service.command.steps;

import com.odeyalo.bot.suiri.domain.AddNewWordMessage;
import com.odeyalo.bot.suiri.service.command.support.state.AddNewWordState;
import com.odeyalo.bot.suiri.service.command.support.state.AddNewWordStateRepository;
import com.odeyalo.bot.suiri.service.command.support.translate.TranslatedWordSuggester;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import static com.odeyalo.bot.suiri.service.command.steps.AddNewWordLanguagePropertiesConstants.ADD_WORD_ORIGINAL_WORD_STEP_MESSAGE_PROPERTY;

@Component
public class OriginalWordAddNewWordMessageBuildingStep extends AbstractAddNewWordMessageBuildingStep {
    private final Logger logger = LoggerFactory.getLogger(OriginalWordAddNewWordMessageBuildingStep.class);
    private final TranslatedWordSuggester translatedWordSuggester;

    @Autowired
    public OriginalWordAddNewWordMessageBuildingStep(AddNewWordStateRepository stateRepository, ResponseMessageResolverDecorator responseMessageResolverDecorator, TranslatedWordSuggester translatedWordSuggester) {
        super(stateRepository, responseMessageResolverDecorator);
        this.translatedWordSuggester = translatedWordSuggester;
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
        this.logger.info("Received original word: {}", text);
        message.setOriginalWord(text);
        String responseMessage = this.responseMessageResolverDecorator.getResponseMessage(update, ADD_WORD_ORIGINAL_WORD_STEP_MESSAGE_PROPERTY);
        SendMessage sendMessage = new SendMessage(chatId, responseMessage);
        ReplyKeyboardMarkup keyboard = getTranslatedWordSuggestionKeyboard(text, responseMessageResolverDecorator.getUserLanguage(update));
        sendMessage.setReplyMarkup(keyboard);
        return sendMessage;
    }

    @Override
    public AddNewWordState getState() {
        return AddNewWordState.ORIGINAL_WORD;
    }


    private ReplyKeyboardMarkup getTranslatedWordSuggestionKeyboard(String originalWord, String userLanguage){
        try {
            Set<String> words = this.translatedWordSuggester.suggestWords(originalWord, userLanguage);
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            keyboardMarkup.setOneTimeKeyboard(true);
            keyboardMarkup.setResizeKeyboard(true);
            KeyboardRow buttons = new KeyboardRow();
            buttons.addAll(new ArrayList<>(words));
            keyboardMarkup.setKeyboard(Collections.singletonList(buttons));
            return keyboardMarkup;
        } catch (Exception ex) {
            this.logger.error("Failed to suggest translated words", ex);
            return null;
        }
    }

}
