package com.odeyalo.bot.suiri.service.sender;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class HttpClientTelegramPhotoSender implements TelegramPhotoSender {
    private final AbsSender sender;

    public HttpClientTelegramPhotoSender(@Qualifier("absSender") AbsSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendPhoto(SendPhoto photo) throws TelegramApiException {
       sender.execute(photo);
    }
}
