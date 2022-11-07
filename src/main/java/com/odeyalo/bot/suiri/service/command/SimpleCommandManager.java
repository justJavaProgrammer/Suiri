package com.odeyalo.bot.suiri.service.command;

import com.odeyalo.bot.suiri.service.callbacks.CallbackQueryHandler;
import com.odeyalo.bot.suiri.service.callbacks.CallbackQueryHandlerDelegate;
import com.odeyalo.bot.suiri.support.IncomingMessageCommandResolver;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SimpleCommandManager implements CommandManager {
    private final CommandExecutorRegistry container;
    private final IncomingMessageCommandResolver commandResolver;
    private final Map<String, CommandExecutor> multiStepCommandsCache;
    private final CallbackQueryHandlerDelegate queryHandlerDelegate;
    private final Logger logger = LoggerFactory.getLogger(SimpleCommandManager.class);

    @Autowired
    public SimpleCommandManager(CommandExecutorRegistry container, IncomingMessageCommandResolver commandResolver, CallbackQueryHandlerDelegate queryHandlerDelegate) {
        this.container = container;
        this.commandResolver = commandResolver;
        this.queryHandlerDelegate = queryHandlerDelegate;
        this.multiStepCommandsCache = new ConcurrentHashMap<>();
    }

    @Override
    public BotApiMethod<?> executeCommand(Update update) {
        this.logger.info("Received update: {}", update);
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return queryHandlerDelegate.handleCallbackQuery(callbackQuery);
        }

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
