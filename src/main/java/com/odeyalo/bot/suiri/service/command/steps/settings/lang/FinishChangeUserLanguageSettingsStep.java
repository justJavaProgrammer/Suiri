package com.odeyalo.bot.suiri.service.command.steps.settings.lang;

import com.odeyalo.bot.suiri.service.command.support.state.settings.ChangeLanguageState;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Finish step of changing user's preferred language
 */
@Service
public class FinishChangeUserLanguageSettingsStep implements ChangeUserLanguageSettingsStep {

    @Override
    public BotApiMethod<?> processStep(Update update, ChangeLanguageMessage message) {
        return new SendMessage(TelegramUtils.getChatId(update), "If you want to change language, type /settings");
    }

    @Override
    public ChangeLanguageState getState() {
        return ChangeLanguageState.FINISH;
    }
}
