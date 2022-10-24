package com.odeyalo.bot.suiri.support;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public class TelegramUtils {

    public static String getText(Update update) {
        return update.getMessage().getText();
    }

    public static String getChatId(Update update) {
        return String.valueOf(update.getMessage().getChatId());
    }

    public static User getTelegramUser(Update update) {
        return update.getMessage().getFrom();
    }
}
