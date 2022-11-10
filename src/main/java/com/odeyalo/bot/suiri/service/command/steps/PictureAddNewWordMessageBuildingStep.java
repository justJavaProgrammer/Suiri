package com.odeyalo.bot.suiri.service.command.steps;

import com.odeyalo.bot.suiri.domain.AddNewWordMessage;
import com.odeyalo.bot.suiri.service.command.support.media.PictureData;
import com.odeyalo.bot.suiri.service.command.support.media.PictureDataResolver;
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
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.odeyalo.bot.suiri.service.command.steps.AddNewWordLanguagePropertiesConstants.ADD_WORD_PICTURE_STEP_MESSAGE_PROPERTY;

@Component
public class PictureAddNewWordMessageBuildingStep extends AbstractAddNewWordMessageBuildingStep {
    private final Logger logger = LoggerFactory.getLogger(PictureAddNewWordMessageBuildingStep.class);
    private final PictureDataResolver pictureDataResolver;

    @Autowired
    public PictureAddNewWordMessageBuildingStep(AddNewWordStateRepository stateRepository, ResponseMessageResolverDecorator responseMessageResolverDecorator, PictureDataResolver pictureDataResolver) {
        super(stateRepository, responseMessageResolverDecorator);
        this.pictureDataResolver = pictureDataResolver;
    }

    @SneakyThrows
    @Override
    public BotApiMethod<?> processStep(Update update, AddNewWordMessage message) {
        String chatId = TelegramUtils.getChatId(update);
        AddNewWordState currentState = getCurrentState(chatId);
        if (currentState != getState()) {
            this.logger.warn("The step was skipped since state was wrong. Expected: {}, received: {}", currentState, getState());
            return null;
        }
        PictureData pictureData = this.pictureDataResolver.getPictureData(update);
        if (pictureData != null) {
            message.setPicture(pictureData.getPictureId());
            message.setPictureType(pictureData.getPictureType());
        }
        String responseMessage = this.responseMessageResolverDecorator.getResponseMessage(update, ADD_WORD_PICTURE_STEP_MESSAGE_PROPERTY);
        return new SendMessage(chatId, responseMessage);
    }


    @Override
    public AddNewWordState getState() {
        return AddNewWordState.PICTURE;
    }
}
