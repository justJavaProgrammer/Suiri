package com.odeyalo.bot.suiri.service.command.steps.settings.test;

import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.entity.UserSettings;
import com.odeyalo.bot.suiri.repository.UserRepository;
import com.odeyalo.bot.suiri.service.command.support.state.settings.test.PreferredTestTypeMessage;
import com.odeyalo.bot.suiri.support.PreferredTypeTextToPreferredTypeCodeConvertor;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.odeyalo.bot.suiri.service.command.steps.settings.SettingsLanguagePropertiesConstants.PREFERRED_TEST_TYPE_ON_CHANGED;

/**
 * Change user's preferred test type setting
 */
@Service
public class ChangeUserPreferredTestTypeSettingsChangeStep implements UserPreferredTestTypeSettingsChangeStep {
    private final UserRepository userRepository;
    private final ResponseMessageResolverDecorator responseMessageResolverDecorator;

    @Autowired
    public ChangeUserPreferredTestTypeSettingsChangeStep(UserRepository userRepository, ResponseMessageResolverDecorator responseMessageResolverDecorator) {
        this.userRepository = userRepository;
        this.responseMessageResolverDecorator = responseMessageResolverDecorator;
    }

    @Override
    public BotApiMethod<?> processStep(Update update, PreferredTestTypeMessage message) {
        String chatId = TelegramUtils.getChatId(update);
        User user = this.userRepository.findUserByTelegramId(chatId);
        String to = PreferredTypeTextToPreferredTypeCodeConvertor.getCode(message.getTo());
        user.getUserSettings().setTestType(UserSettings.PreferredKnowledgeTestType.valueOf(to));
        this.userRepository.save(user);
        String responseMessage = this.responseMessageResolverDecorator.getResponseMessage(update, PREFERRED_TEST_TYPE_ON_CHANGED);
        return new SendMessage(chatId, responseMessage);
    }

    @Override
    public ChangePreferredTestType getType() {
        return ChangePreferredTestType.CHANGE_PREFERRED_TESTING_TYPE;
    }
}
