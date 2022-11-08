package com.odeyalo.bot.suiri.service.command.steps.settings.test;

import com.odeyalo.bot.suiri.service.command.support.state.settings.test.PreferredTestTypeMessage;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Change user's preferred test type(Quiz poll or updatable message)
 */
public interface UserPreferredTestTypeSettingsChangeStep {

    BotApiMethod<?> processStep(Update update, PreferredTestTypeMessage message);

    /**
     * What type this step handles
     * @return - type of this step
     */
    ChangePreferredTestType getType();

}
