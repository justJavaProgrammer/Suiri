package com.odeyalo.bot.suiri.service.command;

import com.odeyalo.bot.suiri.domain.SettingsMessage;
import com.odeyalo.bot.suiri.service.command.support.MultiLanguageCommandTranslatorDelegate;
import com.odeyalo.bot.suiri.service.command.support.state.settings.UserSettingsChanger;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.GenericLanguagePropertiesConstants;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.odeyalo.bot.suiri.service.command.steps.settings.SettingsLanguagePropertiesConstants.*;

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
    private final MultiLanguageCommandTranslatorDelegate translatorDelegate;
    private final ResponseMessageResolverDecorator responseMessageResolverDecorator;
    private final Logger logger = LoggerFactory.getLogger(SettingsCommandExecutor.class);

    @Autowired
    public SettingsCommandExecutor(List<UserSettingsChanger> changers, MultiLanguageCommandTranslatorDelegate translatorDelegate, ResponseMessageResolverDecorator responseMessageResolverDecorator) {
        this.changers = changers.stream().collect(Collectors.toMap(UserSettingsChanger::getName, Function.identity()));
        this.logger.info("Initialized changers map with elements: {}", changers);
        this.lastChangers = new ConcurrentHashMap<>();
        this.translatorDelegate = translatorDelegate;
        this.responseMessageResolverDecorator = responseMessageResolverDecorator;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        String chatId = TelegramUtils.getChatId(update);
        String text = TelegramUtils.getText(update);
        SendMessage message = getDefaultMessage(update);
        if (getCommandName().equals(text)) {
            return message;
        }

        User telegramUser = TelegramUtils.getTelegramUser(update);

        String originalCommand = this.translatorDelegate.getOriginalCommand(update);
        UserSettingsChanger userSettingsChanger = changers.get(originalCommand);
        this.logger.info("Setting changer is: {}", userSettingsChanger);
        if (userSettingsChanger != null) {
            lastChangers.put(chatId, userSettingsChanger);
            return userSettingsChanger.change(update, new SettingsMessage(String.valueOf(telegramUser.getId()), text));
        }

        if (lastChangers.containsKey(chatId)) {
            return lastChangers.get(chatId).change(update, new SettingsMessage(String.valueOf(telegramUser.getId()), text));
        }
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


    private ReplyKeyboardMarkup getKeyboardMarkup(Update update) {
        String language = this.responseMessageResolverDecorator.getResponseMessage(update, GenericLanguagePropertiesConstants.LANGUAGE);
        String notification = this.responseMessageResolverDecorator.getResponseMessage(update, NOTIFICATION_SETTINGS_PROPERTY);
        String preferredTestType = this.responseMessageResolverDecorator.getResponseMessage(update, PREFERRED_TEST_TYPE_VALUE);
        KeyboardRow buttons = new KeyboardRow();
        buttons.add(0, language);
        buttons.add(1, notification);
        buttons.add(2, preferredTestType);
        List<KeyboardRow> keyboard = Collections.singletonList(buttons);
        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup(keyboard);
        replyMarkup.setResizeKeyboard(true);
        replyMarkup.setOneTimeKeyboard(true);
        return replyMarkup;
    }

    private SendMessage getDefaultMessage(Update update) {
        String chatId = TelegramUtils.getChatId(update);
        String responseMessage = this.responseMessageResolverDecorator.getResponseMessage(update, DEFAULT_SETTINGS_MESSAGE_PROPERTY);
        SendMessage message = new SendMessage(chatId, responseMessage);
        ReplyKeyboardMarkup replyMarkup = getKeyboardMarkup(update);
        message.setReplyMarkup(replyMarkup);
        return message;
    }
}
