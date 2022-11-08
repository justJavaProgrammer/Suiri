package com.odeyalo.bot.suiri.service.command.steps.settings.test;

import com.odeyalo.bot.suiri.service.command.support.state.settings.test.PreferredTestTypeMessage;
import com.odeyalo.bot.suiri.support.ReplyKeyboardMarkupUtils;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import static com.odeyalo.bot.suiri.service.command.steps.settings.SettingsLanguagePropertiesConstants.*;

/**
 * First step of changing user's preferred test type
 */
@Component
public class StartUserPreferredTestTypeSettingsChangeStep implements UserPreferredTestTypeSettingsChangeStep {
    private final ResponseMessageResolverDecorator responseMessageResolverDecorator;


    @Autowired
    public StartUserPreferredTestTypeSettingsChangeStep(ResponseMessageResolverDecorator responseMessageResolverDecorator) {
        this.responseMessageResolverDecorator = responseMessageResolverDecorator;
    }

    @Override
    public BotApiMethod<?> processStep(Update update, PreferredTestTypeMessage message) {
        String chatId = TelegramUtils.getChatId(update);
        String responseMessage = this.responseMessageResolverDecorator.getResponseMessage(update, PREFERRED_TEST_TYPE_ON_START_MESSAGE);
        SendMessage sendMessage = new SendMessage(chatId, responseMessage);
        sendMessage.setReplyMarkup(getKeyboard(update));
        return sendMessage;
    }

    @Override
    public ChangePreferredTestType getType() {
        return ChangePreferredTestType.START;
    }


    private ReplyKeyboardMarkup getKeyboard(Update update) {
        String quiz = this.responseMessageResolverDecorator.getResponseMessage(update, PREFERRED_TEST_TYPE_QUIZ_POLL);
        String updatableMessage = this.responseMessageResolverDecorator.getResponseMessage(update, PREFERRED_TEST_TYPE_UPDATABLE_MESSAGE);
        return ReplyKeyboardMarkupUtils.createKeyboard(quiz, updatableMessage);
    }
}
