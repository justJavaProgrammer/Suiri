package com.odeyalo.bot.suiri.configuration;

import com.odeyalo.bot.suiri.service.command.CommandExecutorRegistry;
import com.odeyalo.bot.suiri.support.CommandExecutorRegistryHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.facilities.filedownloader.TelegramFileDownloader;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Configuration
public class TelegramConfiguration {

    @Bean
    public SetWebhook webhook(TelegramBotConfig config) {
        return SetWebhook.builder().url(config.getBotPath())
                .build();
    }

    @Bean
    public Void commandExecutorRegistryHolder(CommandExecutorRegistry registry){
        CommandExecutorRegistryHolder.setRegistry(registry);
        return null;
    }

    @Bean
    public TelegramFileDownloader telegramFileDownloader(TelegramBotConfig config) {
        return new TelegramFileDownloader(config::getToken);
    }

    @Bean
    public AbsSender absSender(TelegramBotConfig config) {
        return new DefaultAbsSender(new DefaultBotOptions()) {
            @Override
            public String getBotToken() {
                return config.getToken();
            }
        };
    }
}
