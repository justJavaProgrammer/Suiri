package com.odeyalo.bot.suiri.service.command.steps;

import com.odeyalo.bot.suiri.domain.AddNewWordMessage;
import com.odeyalo.bot.suiri.service.command.support.AddNewWordState;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface AddNewWordMessageBuildingStep {
    /**
     * Process the given message
     * @param update - update
     * @param message - message
     */

    BotApiMethod<?> processStep(Update update, AddNewWordMessage message);

    /**
     * What state this step handle, if state do not equals to this - the process will be skipped
     * @return - AddNewWordState
     */
    AddNewWordState getState();
}

