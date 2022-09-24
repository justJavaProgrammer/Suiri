package com.odeyalo.bot.suiri.service.command;

import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CommandNotFoundCommandExecutor implements CommandExecutor {
    public static final String COMMAND_NAME = "not_found";

    @Override
    public BotApiMethod<?> execute(Update update) {
        String chatId = TelegramUtils.getChatId(update);
        return new SendMessage(chatId, "The given command is not found");
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public boolean isMultiStepCommand() {
        return false;
    }
}
