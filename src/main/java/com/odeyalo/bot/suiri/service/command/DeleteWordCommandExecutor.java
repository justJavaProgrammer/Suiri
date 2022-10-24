package com.odeyalo.bot.suiri.service.command;

import com.odeyalo.bot.suiri.domain.AddNewWordMessage;
import com.odeyalo.bot.suiri.domain.DeleteWordMessage;
import com.odeyalo.bot.suiri.service.command.steps.delete.DeleteWordStep;
import com.odeyalo.bot.suiri.service.command.support.state.delete.DeleteWordImMemoryStateRepository;
import com.odeyalo.bot.suiri.service.command.support.state.delete.DeleteWordState;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DeleteWordCommandExecutor implements CommandExecutor {

    private final Map<String, DeleteWordMessage> messages = new ConcurrentHashMap<>();
    private final DeleteWordImMemoryStateRepository stateRepository;
    private final Map<DeleteWordState, DeleteWordStep> steps;
    private final Logger logger = LoggerFactory.getLogger(DeleteWordCommandExecutor.class);

    @Autowired
    public DeleteWordCommandExecutor(List<DeleteWordStep> steps, DeleteWordImMemoryStateRepository stateRepository) {
        this.stateRepository = stateRepository;
        this.steps = convertToMap(steps);
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        String text = update.getMessage().getText();
        String chatId = TelegramUtils.getChatId(update);
        DeleteWordMessage message = new DeleteWordMessage();
        DeleteWordMessage cachedMessage = messages.get(chatId);

        if (cachedMessage != null) {
            message = cachedMessage;
        }
        DeleteWordState state = stateRepository.findStateById(chatId);
        if (state == null || getCommandName().equals(text)) {
            state = DeleteWordState.START;
        }
        DeleteWordStep step = steps.get(state);
        BotApiMethod<?> method = null;

        if (step != null) {
            this.logger.info("The current state is: {}", state);
            method = step.processStep(update, message);
            DeleteWordState nextState = state.next();
            this.stateRepository.saveState(chatId, nextState);
        }

        DeleteWordState updatedState = stateRepository.findStateById(chatId);

        if (updatedState == DeleteWordState.FINISH) {
            this.logger.info("Finish state! Word is deleted");
            this.messages.remove(chatId);
        }

        return method;
    }

    @Override
    public String getCommandName() {
        return "/delete";
    }

    @Override
    public boolean isMultiStepCommand() {
        return true;
    }


    private Map<DeleteWordState, DeleteWordStep> convertToMap(List<DeleteWordStep> steps) {
        return steps.stream().collect(Collectors.toMap(DeleteWordStep::getState, Function.identity()));
    }
}
