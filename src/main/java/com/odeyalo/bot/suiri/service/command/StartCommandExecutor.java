package com.odeyalo.bot.suiri.service.command;

import com.odeyalo.bot.suiri.entity.Dictionary;
import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.repository.UserRepository;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommandExecutor implements CommandExecutor {
    public static final String COMMAND_NAME = "/start";
    private final UserRepository userRepository;

    public StartCommandExecutor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        org.telegram.telegrambots.meta.api.objects.User telegramUser = TelegramUtils.getTelegramUser(update);
        User user = User.builder()
                .firstName(telegramUser.getFirstName())
                .lastName(telegramUser.getLastName())
                .telegramId(String.valueOf(telegramUser.getId()))
                .build();
        Dictionary dictionary = Dictionary.builder()
                .user(user)
                .build();
        user.setUserDictionary(dictionary);
        this.userRepository.save(user);
        return new SendMessage(String.valueOf(chatId),
                "Welcome to the Suiri Bot. Type /help to see all commands");
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
