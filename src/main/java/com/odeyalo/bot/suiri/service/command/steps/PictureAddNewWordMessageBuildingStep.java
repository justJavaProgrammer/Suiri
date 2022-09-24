package com.odeyalo.bot.suiri.service.command.steps;

import com.odeyalo.bot.suiri.domain.AddNewWordMessage;
import com.odeyalo.bot.suiri.service.command.support.AddNewWordState;
import com.odeyalo.bot.suiri.service.command.support.AddNewWordStateRepository;
import com.odeyalo.bot.suiri.support.TelegramFileSaver;
import com.odeyalo.bot.suiri.support.TelegramUtils;
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
    private final TelegramFileSaver fileSaver;
    private static final byte BEST_QUALITY_TELEGRAM_IMAGE_INDEX = 2;

    @Autowired
    public PictureAddNewWordMessageBuildingStep(AddNewWordStateRepository stateRepository, TelegramFileSaver fileSaver) {
        super(stateRepository);
        this.fileSaver = fileSaver;
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
        List<PhotoSize> filePath = update.getMessage().getPhoto();
        PhotoSize photoSize = filePath.get(BEST_QUALITY_TELEGRAM_IMAGE_INDEX);
        String fileId = photoSize.getFileId();
        this.fileSaver.save(fileId);
        this.stateRepository.saveState(chatId, AddNewWordState.FINISH);
        return new SendMessage(chatId, "Success saved the word!");
    }

    @Override
    public AddNewWordState getState() {
        return AddNewWordState.PICTURE;
    }
}
