package com.odeyalo.bot.suiri.service.command;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandManager {

    BotApiMethod<?> executeCommand(Update update);

    String resolveCommandName(Update update);

}
