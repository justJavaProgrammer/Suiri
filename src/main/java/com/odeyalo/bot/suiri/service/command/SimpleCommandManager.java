package com.odeyalo.bot.suiri.service.command;

import com.odeyalo.bot.suiri.support.IncomingMessageCommandResolver;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SimpleCommandManager implements CommandManager {
    private final CommandExecutorRegistry container;
    private final IncomingMessageCommandResolver commandResolver;
    private final Map<String, CommandExecutor> multiStepCommandsCache;

    @Autowired
    public SimpleCommandManager(CommandExecutorRegistry container, IncomingMessageCommandResolver commandResolver) {
        this.container = container;
        this.commandResolver = commandResolver;
        this.multiStepCommandsCache = new ConcurrentHashMap<>();
    }

    @Override
    public BotApiMethod<?> executeCommand(Update update) {
        String commandName = resolveCommandName(update);
        CommandExecutor executor = container.getByCommandName(commandName);
        CommandExecutor lastExecutor = multiStepCommandsCache.get(TelegramUtils.getChatId(update));

        if (executor instanceof CommandNotFoundCommandExecutor && lastExecutor != null) {
            return lastExecutor.execute(update);
        }

        boolean multiStepCommand = executor.isMultiStepCommand();
        if (multiStepCommand) {
            this.multiStepCommandsCache.put(TelegramUtils.getChatId(update), executor);
        }
        return executor.execute(update);
    }

    @Override
    public String resolveCommandName(Update update) {
        return commandResolver.getCommandName(TelegramUtils.getText(update));
    }
}
