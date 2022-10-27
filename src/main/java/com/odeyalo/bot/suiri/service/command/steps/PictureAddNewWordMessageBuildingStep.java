package com.odeyalo.bot.suiri.service.command.steps;

import com.odeyalo.bot.suiri.domain.AddNewWordMessage;
import com.odeyalo.bot.suiri.service.command.support.state.AddNewWordState;
import com.odeyalo.bot.suiri.service.command.support.state.AddNewWordStateRepository;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class PictureAddNewWordMessageBuildingStep extends AbstractAddNewWordMessageBuildingStep {
    private final Logger logger = LoggerFactory.getLogger(PictureAddNewWordMessageBuildingStep.class);

    @Autowired
    public PictureAddNewWordMessageBuildingStep(AddNewWordStateRepository stateRepository, ResponseMessageResolverDecorator responseMessageResolverDecorator) {
        super(stateRepository, responseMessageResolverDecorator);
    }

    @SneakyThrows
    @Override
    public BotApiMethod<?> processStep(Update update, AddNewWordMessage message) {
        String chatId = TelegramUtils.getChatId(update);
        AddNewWordState currentState = getCurrentState(chatId);
        if (currentState != getState()) {
            this.logger.warn("The step was skipped since state was wrong. Expected: {}, received: {}",  currentState, getState());
            return null;
        }
        String fileId = getFileId(update);
        message.setPicture(fileId);
        String responseMessage = this.responseMessageResolverDecorator.getResponseMessage(update, "language.word.add.step.picture");
        return new SendMessage(chatId, responseMessage);
    }


    @Override
    public AddNewWordState getState() {
        return AddNewWordState.PICTURE;
    }


    private String getFileId(Update update) {
        List<PhotoSize> filePath = update.getMessage().getPhoto();
        int index = filePath.size() - 1;
        PhotoSize photoSize = filePath.get(index);
        return photoSize.getFileId();
    }

}
