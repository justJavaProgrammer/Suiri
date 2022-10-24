package com.odeyalo.bot.suiri.service.command.steps;

import com.odeyalo.bot.suiri.domain.AddNewWordMessage;
import com.odeyalo.bot.suiri.service.command.support.state.AddNewWordState;
import com.odeyalo.bot.suiri.service.command.support.state.AddNewWordStateRepository;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartAddNewWordMessageBuildingStep extends AbstractAddNewWordMessageBuildingStep {

    @Autowired
    public StartAddNewWordMessageBuildingStep(AddNewWordStateRepository stateRepository) {
        super(stateRepository);
    }

    @Override
    public BotApiMethod<?> processStep(Update update, AddNewWordMessage message) {
        String chatId = TelegramUtils.getChatId(update);
        return new SendMessage(chatId, "Please, send the original word");
    }

    @Override
    public AddNewWordState getState() {
        return AddNewWordState.START;
    }
}
