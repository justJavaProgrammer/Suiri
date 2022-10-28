package com.odeyalo.bot.suiri.service.command.steps.settings.lang;

import com.odeyalo.bot.suiri.service.command.support.state.settings.ChangeLanguageState;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Finish step of changing user's preferred language
 */
@Service
public class FinishChangeUserLanguageSettingsStep implements ChangeUserLanguageSettingsStep {
    private final ResponseMessageResolverDecorator responseMessageResolverDecorator;

    @Autowired
    public FinishChangeUserLanguageSettingsStep(ResponseMessageResolverDecorator responseMessageResolverDecorator) {
        this.responseMessageResolverDecorator = responseMessageResolverDecorator;
    }

    @Override
    public BotApiMethod<?> processStep(Update update, ChangeLanguageMessage message) {
        String response = responseMessageResolverDecorator.getResponseMessage(update, ChangeUserLanguageLanguagePropertiesConstants.FINISH);
        return new SendMessage(TelegramUtils.getChatId(update), response);
    }

    @Override
    public ChangeLanguageState getState() {
        return ChangeLanguageState.FINISH;
    }
}
