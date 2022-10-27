package com.odeyalo.bot.suiri.service.command.steps.settings.lang;

import com.odeyalo.bot.suiri.service.command.support.state.settings.ChangeLanguageState;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class StartChangeUserLanguageSettingsStep implements ChangeUserLanguageSettingsStep {

    @Override
    public BotApiMethod<?> processStep(Update update, ChangeLanguageMessage message) {
        String chatId = TelegramUtils.getChatId(update);
        ReplyKeyboardMarkup keyboardMarkup = getKeyboardMarkup();
        SendMessage sendMessage = new SendMessage(chatId, "Choose language that you want to use");
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    @Override
    public ChangeLanguageState getState() {
        return ChangeLanguageState.START;
    }


    private ReplyKeyboardMarkup getKeyboardMarkup() {
        KeyboardButton ukrainian = new KeyboardButton("Ukrainian");
        KeyboardButton english = new KeyboardButton("English");
        ArrayList<KeyboardButton> buttons = new ArrayList<>();
        buttons.add(ukrainian);
        buttons.add(english);
        KeyboardRow row = new KeyboardRow(buttons);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(Collections.singletonList(row));
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        return keyboardMarkup;
    }
}
