package com.odeyalo.bot.suiri.service.command.steps.settings.lang;

import com.odeyalo.bot.suiri.service.command.support.state.settings.ChangeLanguageState;
import com.odeyalo.bot.suiri.support.ReplyKeyboardMarkupUtils;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.GenericLanguagePropertiesConstants;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Service
public class StartChangeUserLanguageSettingsStep implements ChangeUserLanguageSettingsStep {
    private final ResponseMessageResolverDecorator responseMessageResolverDecorator;

    @Autowired
    public StartChangeUserLanguageSettingsStep(ResponseMessageResolverDecorator responseMessageResolverDecorator) {
        this.responseMessageResolverDecorator = responseMessageResolverDecorator;
    }

    @Override
    public BotApiMethod<?> processStep(Update update, ChangeLanguageMessage message) {
        String chatId = TelegramUtils.getChatId(update);
        ReplyKeyboardMarkup keyboardMarkup = getKeyboardMarkup(update);
        String responseMessage = this.responseMessageResolverDecorator.getResponseMessage(update, ChangeUserLanguageLanguagePropertiesConstants.START);
        SendMessage sendMessage = new SendMessage(chatId, responseMessage);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    @Override
    public ChangeLanguageState getState() {
        return ChangeLanguageState.START;
    }


    private ReplyKeyboardMarkup getKeyboardMarkup(Update update) {
        String englishLang = this.responseMessageResolverDecorator.getResponseMessage(update, GenericLanguagePropertiesConstants.ENGLISH_LANGUAGE);
        String ukrainianLanguage = this.responseMessageResolverDecorator.getResponseMessage(update, GenericLanguagePropertiesConstants.UKRAINIAN_LANGUAGE);
        return ReplyKeyboardMarkupUtils.createKeyboard(englishLang, ukrainianLanguage);
    }
}
