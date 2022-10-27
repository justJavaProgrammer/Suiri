package com.odeyalo.bot.suiri.service.command;

import com.odeyalo.bot.suiri.entity.Dictionary;
import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.entity.UserSettings;
import com.odeyalo.bot.suiri.repository.UserRepository;
import com.odeyalo.bot.suiri.support.lang.UserLocaleLanguageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommandExecutor implements CommandExecutor {
    public static final String COMMAND_NAME = "/start";
    private final UserRepository userRepository;
    private final UserLocaleLanguageResolver languageResolver;

    @Autowired
    public StartCommandExecutor(UserRepository userRepository, UserLocaleLanguageResolver languageResolver) {
        this.userRepository = userRepository;
        this.languageResolver = languageResolver;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        User user = userRepository.findUserByTelegramId(String.valueOf(chatId));
        if (user != null) {
            return new SendMessage(String.valueOf(chatId),
                    "We are remember you! Welcome back, " + user.getFirstName());
        }
        saveUser(update);
        return new SendMessage(String.valueOf(chatId),
                "Welcome to the Suiri Bot. Type /help to see all commands");
    }

    private void saveUser(Update update) {
        org.telegram.telegrambots.meta.api.objects.User telegramUser = update.getMessage().getFrom();
        User user = User.builder()
                .firstName(telegramUser.getFirstName())
                .lastName(telegramUser.getLastName())
                .telegramId(String.valueOf(telegramUser.getId()))
                .build();
        Dictionary dictionary = Dictionary.builder()
                .user(user)
                .build();
        String userLanguage = languageResolver.resolveLang(update);
        UserSettings userSettings = UserSettings.builder()
                .user(user)
                .enableNotification(false)
                .language(userLanguage)
                .build();
        user.setUserSettings(userSettings);
        user.setUserDictionary(dictionary);
        this.userRepository.save(user);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public boolean isMultiStepCommand() {
        return false;
    }
}
