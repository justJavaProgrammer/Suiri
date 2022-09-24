package com.odeyalo.bot.suiri.service.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultCommandExecutorContainer implements CommandExecutorRegistry {
    private final Map<String, CommandExecutor> executors;
    private final Logger logger = LoggerFactory.getLogger(DefaultCommandExecutorContainer.class);

    @Autowired
    public DefaultCommandExecutorContainer() {
        this.executors = new HashMap<>();
    }

    /**
     * Initialize container with the given executors
     * @param executors - executors to registry
     */
    public DefaultCommandExecutorContainer(Map<String, CommandExecutor> executors) {
        this.executors = executors;
    }

    @Override
    public void registerCommandExecutor(String commandName, CommandExecutor executor) {
        boolean contains = contains(commandName);
        if (contains) {
            throw new IllegalArgumentException(String.format("The command with name: %s already exist", commandName));
        }
        this.executors.put(commandName, executor);
        this.logger.info("Registered: {} with command name {}", executor, commandName);
    }

    @Override
    public boolean contains(String commandName) {
        return executors.containsKey(commandName);
    }

    @Override
    public CommandExecutor getByCommandName(String commandName) {
        return executors.getOrDefault(commandName, new CommandNotFoundCommandExecutor());
    }

    @Override
    public void delete(String commandName) {
        this.executors.remove(commandName);
    }
}
