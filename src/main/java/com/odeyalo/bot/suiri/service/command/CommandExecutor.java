package com.odeyalo.bot.suiri.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Main interface to execute the given update
 */
public interface CommandExecutor {
    /**
     * Executes the given command and returns BotApiMethod
     * @param update = update
     * @return - BotApiMethod
     */
    BotApiMethod<?> execute(Update update);

    String getCommandName();

    boolean isMultiStepCommand();

    @Autowired
    default void registerMe(CommandExecutorRegistry registry) {
        registry.registerCommandExecutor(getCommandName(), this);
    }
}
