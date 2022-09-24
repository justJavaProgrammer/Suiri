package com.odeyalo.bot.suiri.domain;

import com.odeyalo.bot.suiri.configuration.TelegramBotConfig;
import com.odeyalo.bot.suiri.service.command.CommandManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Component
public class BotApiMethodEntrypoint extends SpringWebhookBot {
    private final TelegramBotConfig config;
    private final CommandManager manager;

    public BotApiMethodEntrypoint(TelegramBotConfig config,
                                  SetWebhook setWebhook,
                                  CommandManager manager) {
        super(setWebhook);
        this.config = config;
        this.manager = manager;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return this.manager.executeCommand(update);
    }

    @Override
    public String getBotPath() {
        return config.getBotPath();
    }
}
