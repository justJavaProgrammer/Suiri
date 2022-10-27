package com.odeyalo.bot.suiri.service.command;

import com.odeyalo.bot.suiri.domain.SettingsMessage;
import com.odeyalo.bot.suiri.service.command.support.state.settings.UserSettingsChanger;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * CommandExecutor that executes '/settings' command and change user's settings
 */
@Service
public class SettingsCommandExecutor implements CommandExecutor {
    /**
     * Map with name of the setting that this Changer is handle by key and value by UserSettingsChanger
     */
    private final Map<String, UserSettingsChanger> changers;
    /**
     * Map with user's id and last executed UserSettingsChanger for this user
     */
    private final Map<String, UserSettingsChanger> lastChangers;

    @Autowired
    public SettingsCommandExecutor(List<UserSettingsChanger> changers, Map<String, UserSettingsChanger> lastChangers) {
        this.changers = changers.stream().collect(Collectors.toMap(UserSettingsChanger::getName, Function.identity()));
        this.lastChangers = lastChangers;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        String chatId = TelegramUtils.getChatId(update);
        String text = TelegramUtils.getText(update);
        SendMessage message = getDefaultMessage(chatId);
        if (getCommandName().equals(text)) {
            return message;
        }

        User telegramUser = TelegramUtils.getTelegramUser(update);

        if (lastChangers.containsKey(chatId)) {
            return lastChangers.get(chatId).change(update, new SettingsMessage(String.valueOf(telegramUser.getId()), text));
        }
        UserSettingsChanger userSettingsChanger = changers.get(text);
        lastChangers.put(chatId, userSettingsChanger);
        return userSettingsChanger.change(update, new SettingsMessage(String.valueOf(telegramUser.getId()), text));

    }

    private SendMessage getDefaultMessage(String chatId) {
        SendMessage message = new SendMessage(chatId, "Choose what you want to change");
        ReplyKeyboardMarkup replyMarkup = getKeyboardMarkup();
        message.setReplyMarkup(replyMarkup);
        return message;
    }

    @Override
    public String getCommandName() {
        return "/settings";
    }

    @Override
    public boolean isMultiStepCommand() {
        return true;
    }


    private ReplyKeyboardMarkup getKeyboardMarkup() {
        KeyboardRow buttons = new KeyboardRow();
        buttons.add(0, "Language");
        List<KeyboardRow> keyboard = Collections.singletonList(buttons);
        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup(keyboard);
        replyMarkup.setResizeKeyboard(true);
        return replyMarkup;
    }
}
