package com.odeyalo.bot.suiri.service.command.steps;

import com.odeyalo.bot.suiri.domain.AddNewWordMessage;
import com.odeyalo.bot.suiri.service.command.support.state.AddNewWordState;
import com.odeyalo.bot.suiri.service.command.support.state.AddNewWordStateRepository;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartAddNewWordMessageBuildingStep extends AbstractAddNewWordMessageBuildingStep {

    @Autowired
    public StartAddNewWordMessageBuildingStep(AddNewWordStateRepository stateRepository, ResponseMessageResolverDecorator responseMessageResolverDecorator) {
        super(stateRepository, responseMessageResolverDecorator);
    }

    @Override
    public BotApiMethod<?> processStep(Update update, AddNewWordMessage message) {
        String chatId = TelegramUtils.getChatId(update);
        String responseMessage = responseMessageResolverDecorator.getResponseMessage(update, "language.word.add.step.start");
        return new SendMessage(chatId, responseMessage);
    }

    @Override
    public AddNewWordState getState() {
        return AddNewWordState.START;
    }
}
