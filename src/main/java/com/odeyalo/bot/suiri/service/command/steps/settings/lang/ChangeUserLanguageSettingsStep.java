package com.odeyalo.bot.suiri.service.command.steps.settings.lang;

import com.odeyalo.bot.suiri.service.command.support.state.settings.ChangeLanguageState;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Step to change user's preferred language
 */
public interface ChangeUserLanguageSettingsStep {

    BotApiMethod<?> processStep(Update update, ChangeLanguageMessage message);

    /**
     * What state this step handles
     * @return - state of this step
     */
    ChangeLanguageState getState();

}
