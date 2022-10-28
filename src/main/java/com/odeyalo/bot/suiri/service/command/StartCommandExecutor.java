package com.odeyalo.bot.suiri.service.command;

import com.odeyalo.bot.suiri.entity.Dictionary;
import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.entity.UserSettings;
import com.odeyalo.bot.suiri.repository.UserRepository;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import com.odeyalo.bot.suiri.support.lang.UserLocaleLanguageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.odeyalo.bot.suiri.service.command.support.StartCommandLanguagePropertiesConstants.ON_USER_EXIST_PROPERTY;
import static com.odeyalo.bot.suiri.service.command.support.StartCommandLanguagePropertiesConstants.ON_USER_NOT_EXIST_PROPERTY;

@Component
public class StartCommandExecutor implements CommandExecutor {
    public static final String COMMAND_NAME = "/start";
    private final UserRepository userRepository;
    private final UserLocaleLanguageResolver languageResolver;
    private final ResponseMessageResolverDecorator responseMessageResolverDecorator;

    @Autowired
    public StartCommandExecutor(UserRepository userRepository, UserLocaleLanguageResolver languageResolver, ResponseMessageResolverDecorator responseMessageResolverDecorator) {
        this.userRepository = userRepository;
        this.languageResolver = languageResolver;
        this.responseMessageResolverDecorator = responseMessageResolverDecorator;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        User user = userRepository.findUserByTelegramId(String.valueOf(chatId));
        if (user != null) {
            String responseMessage = this.responseMessageResolverDecorator.getResponseMessage(update, ON_USER_EXIST_PROPERTY);
            return new SendMessage(String.valueOf(chatId),
                    responseMessage + " " + user.getFirstName());
        }
        saveUser(update);
        String responseMessage = this.responseMessageResolverDecorator.getResponseMessage(update, ON_USER_NOT_EXIST_PROPERTY);
        return new SendMessage(String.valueOf(chatId), responseMessage);
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
