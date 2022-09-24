package com.odeyalo.bot.suiri.support;

import com.odeyalo.bot.suiri.service.command.CommandExecutorRegistry;

/**
 * Hold the CommandExecutorRegistry to access using static methods
 */
public class CommandExecutorRegistryHolder {
    private static CommandExecutorRegistry registry;

    public static CommandExecutorRegistry getRegistry() {
        return registry;
    }

    public static void setRegistry(CommandExecutorRegistry registry) {
        CommandExecutorRegistryHolder.registry = registry;
    }
}
