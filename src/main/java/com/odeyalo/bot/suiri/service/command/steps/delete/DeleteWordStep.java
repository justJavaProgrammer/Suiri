package com.odeyalo.bot.suiri.service.command.steps.delete;

import com.odeyalo.bot.suiri.domain.DeleteWordMessage;
import com.odeyalo.bot.suiri.service.command.support.state.delete.DeleteWordState;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface DeleteWordStep {

    BotApiMethod<?> processStep(Update update, DeleteWordMessage message);


    DeleteWordState getState();
}
