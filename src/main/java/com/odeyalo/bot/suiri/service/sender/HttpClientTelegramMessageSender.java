package com.odeyalo.bot.suiri.service.sender;

import com.odeyalo.bot.suiri.configuration.TelegramBotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@Component
public class HttpClientTelegramMessageSender implements TelegramMessageSender {
    private final RestTemplate restTemplate;
    private final String token;

    @Autowired
    public HttpClientTelegramMessageSender(RestTemplate restTemplate, TelegramBotConfig config) {
        this.restTemplate = restTemplate;
        this.token = config.getToken();
    }

    @Override
    public void send(String chatId, String text) {
        URI uri = UriBuilder
                .fromUri("https://api.telegram.org")
                .path("/bot" + token +"/sendMessage")
                .queryParam("chat_id", chatId)
                .queryParam("text", text)
                .build();
        RequestEntity<?> entity = new RequestEntity<>(HttpMethod.GET, uri);
        this.restTemplate.exchange(entity, String.class);
    }
}
