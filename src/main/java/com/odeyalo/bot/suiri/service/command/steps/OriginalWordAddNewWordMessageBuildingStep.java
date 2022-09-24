package com.odeyalo.bot.suiri.service.command.steps;

import com.odeyalo.bot.suiri.domain.AddNewWordMessage;
import com.odeyalo.bot.suiri.service.command.support.AddNewWordState;
import com.odeyalo.bot.suiri.service.command.support.AddNewWordStateRepository;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class OriginalWordAddNewWordMessageBuildingStep extends AbstractAddNewWordMessageBuildingStep {
    private final Logger logger = LoggerFactory.getLogger(OriginalWordAddNewWordMessageBuildingStep.class);

    @Autowired
    public OriginalWordAddNewWordMessageBuildingStep(AddNewWordStateRepository stateRepository) {
        super(stateRepository);
    }

    @Override
    public BotApiMethod<?> processStep(Update update, AddNewWordMessage message) {
        String chatId = TelegramUtils.getChatId(update);
        AddNewWordState currentState = getCurrentState(chatId);
        if (currentState != getState()) {
            this.logger.warn("The step was skipped since state was wrong. Expected: {}, received: {}",  currentState, getState());
            return null;
        }

        String text = TelegramUtils.getText(update);
        this.logger.info("Received original word: {}", text);
        message.setOriginalWord(text);
        this.stateRepository.saveState(chatId, AddNewWordState.TRANSLATED_WORD);
        return new SendMessage(chatId, "Now send the translated word");
    }

    @Override
    public AddNewWordState getState() {
        return AddNewWordState.ORIGINAL_WORD;
    }
}