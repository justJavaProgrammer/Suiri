package com.odeyalo.bot.suiri.service.command;

import com.odeyalo.bot.suiri.domain.AddNewWordMessage;
import com.odeyalo.bot.suiri.service.command.steps.AddNewWordMessageBuildingStep;
import com.odeyalo.bot.suiri.service.command.support.AddNewWordState;
import com.odeyalo.bot.suiri.service.command.support.AddNewWordStateRepository;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AddNewWordCommandExecutor implements CommandExecutor {
    private final Map<String, AddNewWordMessage> messages = new ConcurrentHashMap<>();
    private final List<AddNewWordMessageBuildingStep> steps;
    private final AddNewWordStateRepository stateRepository;

    public AddNewWordCommandExecutor(List<AddNewWordMessageBuildingStep> steps, AddNewWordStateRepository stateRepository) {
        this.steps = steps;
        this.stateRepository = stateRepository;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        String text = TelegramUtils.getText(update);
        String chatId = TelegramUtils.getChatId(update);
        Long id = TelegramUtils.getTelegramUser(update).getId();
        AddNewWordMessage message = new AddNewWordMessage();
        message.setUserId(String.valueOf(id));
        AddNewWordMessage cachedMessage = messages.get(chatId);
        if (cachedMessage != null) {
            message = cachedMessage;
        }
        AddNewWordState currentState = stateRepository.findStateById(chatId);
        if (currentState == null || getCommandName().equals(text)) {
            stateRepository.saveState(chatId, AddNewWordState.START);
            currentState = AddNewWordState.START;
        }

        BotApiMethod<?> method = null;
        for (AddNewWordMessageBuildingStep step : steps) {
            if (step.getState() == currentState) {
                method = step.processStep(update, message);
            }
        }
        messages.put(chatId, message);
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
}
