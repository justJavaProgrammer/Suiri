package com.odeyalo.bot.suiri.service.command;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@Component
public class StartCommandExecutor implements CommandExecutor {
    public static final String COMMAND_NAME = "/start";

    @Override
    public BotApiMethod<?> execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        return new SendMessage(String.valueOf(chatId),
                "Welcome to the Suiri Bot. Type /help to see all commands");
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
