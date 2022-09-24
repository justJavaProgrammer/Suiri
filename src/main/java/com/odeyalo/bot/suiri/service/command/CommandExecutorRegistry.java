package com.odeyalo.bot.suiri.service.command;

import com.odeyalo.bot.suiri.exception.CommandNotFoundException;

/**
 * Simple registry to register the CommandExecutor in container.
 * @see CommandExecutor
 */
public interface CommandExecutorRegistry {
    /**
     * Register the CommandExecutor inside container
     * @param commandName - key to registry
     * @param executor - value that will be registered
     */
    void registerCommandExecutor(String commandName, CommandExecutor executor);

    /**
     * Check if container already contains the given command name
     * @param commandName - command name to check
     * @return - true if command already existed inside container
     */
    boolean contains(String commandName);

    /**
     * Return specific CommandExecutor by command name
     * @param commandName - command name to search for
     * @return - CommandExecutor by command name
     * @throws CommandNotFoundException - if command was not found
     */
    CommandExecutor getByCommandName(String commandName) throws CommandNotFoundException;

    /**
     * Delete CommandExecutor by command name
     * @param commandName - command name
     */
    void delete(String commandName);
}
