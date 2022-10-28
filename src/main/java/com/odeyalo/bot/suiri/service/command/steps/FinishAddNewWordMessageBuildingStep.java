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

import static com.odeyalo.bot.suiri.service.command.steps.AddNewWordLanguagePropertiesConstants.ADD_WORD_FINISH_STEP_MESSAGE_PROPERTY;

@Component
public class FinishAddNewWordMessageBuildingStep extends AbstractAddNewWordMessageBuildingStep {

    @Autowired
    public FinishAddNewWordMessageBuildingStep(AddNewWordStateRepository stateRepository, ResponseMessageResolverDecorator responseMessageResolverDecorator) {
        super(stateRepository, responseMessageResolverDecorator);
    }

    @Override
    public BotApiMethod<?> processStep(Update update, AddNewWordMessage message) {
        String chatId = TelegramUtils.getChatId(update);
        String responseMessage = this.responseMessageResolverDecorator.getResponseMessage(update, ADD_WORD_FINISH_STEP_MESSAGE_PROPERTY);
        return new SendMessage(chatId, responseMessage);
    }

    @Override
    public AddNewWordState getState() {
        return AddNewWordState.FINISH;
    }
}
