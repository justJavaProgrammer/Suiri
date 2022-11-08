package com.odeyalo.bot.suiri.support;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReplyKeyboardMarkupUtils {

    public static ReplyKeyboardMarkup createKeyboard(String... texts) {
        List<KeyboardButton> buttons = Arrays.stream(texts).map(text -> new KeyboardButton(text)).collect(Collectors.toList());
        KeyboardRow row = new KeyboardRow(buttons);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(Collections.singletonList(row));
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        return keyboardMarkup;
    }
}
