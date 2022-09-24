package com.odeyalo.bot.suiri.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TelegramBotConfig {
    @Value("${app.telegram.bot.name}")
    private String botName;
    @Value("${app.telegram.bot.token}")
    private String token;
    @Value("${app.telegram.bot.api.path}")
    private String botPath;
    @Value("${app.telegram.bot.api.path}")
    private String apiPath;
}
