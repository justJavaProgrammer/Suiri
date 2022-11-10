package com.odeyalo.bot.suiri.service.command;

import com.odeyalo.bot.suiri.domain.AddNewWordMessage;
import com.odeyalo.bot.suiri.entity.DictionaryItem;
import com.odeyalo.bot.suiri.service.command.steps.AddNewWordMessageBuildingStep;
import com.odeyalo.bot.suiri.service.command.support.state.AddNewWordState;
import com.odeyalo.bot.suiri.service.command.support.state.AddNewWordStateRepository;
import com.odeyalo.bot.suiri.service.dictionary.item.add.UserDictionaryUpdater;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AddNewWordCommandExecutor implements CommandExecutor {
    private final Map<String, AddNewWordMessage> messages = new ConcurrentHashMap<>();
    private final Map<AddNewWordState, AddNewWordMessageBuildingStep> steps;
    private final AddNewWordStateRepository stateRepository;
    private final UserDictionaryUpdater updater;
    private final Logger logger = LoggerFactory.getLogger(AddNewWordCommandExecutor.class);

    @Autowired
    public AddNewWordCommandExecutor(List<AddNewWordMessageBuildingStep> steps,
                                     AddNewWordStateRepository stateRepository,
                                     UserDictionaryUpdater updater) {
        this.stateRepository = stateRepository;
        this.updater = updater;
        this.steps = convertToMap(steps);

    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        String text = TelegramUtils.getText(update);
        String chatId = TelegramUtils.getChatId(update);
        Long userId = TelegramUtils.getTelegramUser(update).getId();

        AddNewWordMessage message = getMessage(chatId, userId);

        AddNewWordState currentState = getCurrentState(text, chatId);

        BotApiMethod<?> method = null;

        AddNewWordMessageBuildingStep step = steps.get(currentState);

        if (step != null) {
            method = doProcessStep(update, chatId, message, currentState, step);
        }

        messages.put(chatId, message);

        AddNewWordState updatedState = stateRepository.findStateById(chatId);

        if (updatedState == AddNewWordState.FINISH) {
            onFinishState(update, chatId, message);
        }

        return method;
    }

    private BotApiMethod<?> doProcessStep(Update update, String chatId, AddNewWordMessage message, AddNewWordState currentState, AddNewWordMessageBuildingStep step) {
        BotApiMethod<?> method = step.processStep(update, message);
        AddNewWordState nextState = currentState.next();
        this.stateRepository.saveState(chatId, nextState);
        this.logger.info("Step: {} has been worked success, current state is: {}", step.getClass().getName(), nextState);
        return method;
    }

    @Override
    public String getCommandName() {
        return "/add";
    }

    @Override
    public boolean isMultiStepCommand() {
        return true;
    }


    private AddNewWordMessage getMessage(String chatId, Long userId) {
        AddNewWordMessage message = new AddNewWordMessage();
        message.setUserId(String.valueOf(userId));

        AddNewWordMessage cachedMessage = messages.get(chatId);
        if (cachedMessage != null) {
            message = cachedMessage;
        }
        return message;
    }

    private AddNewWordState getCurrentState(String text, String chatId) {
        AddNewWordState currentState = stateRepository.findStateById(chatId);
        if (currentState == null || getCommandName().equals(text)) {
            currentState = AddNewWordState.START;
            stateRepository.saveState(chatId, currentState);
        }
        return currentState;
    }


    private void onFinishState(Update update, String chatId, AddNewWordMessage message) {
        User telegramUser = TelegramUtils.getTelegramUser(update);
        DictionaryItem dictionaryItem = convertToDictionaryItem(message);
        updater.addWordToUser(String.valueOf(telegramUser.getId()), dictionaryItem);
        stateRepository.saveState(chatId, AddNewWordState.START);
        messages.remove(chatId);
    }


    private DictionaryItem convertToDictionaryItem(AddNewWordMessage message) {
        return DictionaryItem.builder()
                .translatedText(message.getTranslatedWords().get(0))
                .originalText(message.getOriginalWord())
                .picture(message.getPicture())
                .pictureType(message.getPictureType())
                .build();
    }

    private Map<AddNewWordState, AddNewWordMessageBuildingStep> convertToMap(List<AddNewWordMessageBuildingStep> steps) {
        return steps.stream().collect(Collectors.toMap(AddNewWordMessageBuildingStep::getState, Function.identity()));
    }
}
