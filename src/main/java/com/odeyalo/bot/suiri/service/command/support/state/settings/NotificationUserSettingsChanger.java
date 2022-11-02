package com.odeyalo.bot.suiri.service.command.support.state.settings;

import com.odeyalo.bot.suiri.domain.SettingsMessage;
import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.repository.UserRepository;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.odeyalo.bot.suiri.service.command.steps.settings.SettingsLanguagePropertiesConstants.ON_NOTIFICATION_DISABLED_PROPERTY;
import static com.odeyalo.bot.suiri.service.command.steps.settings.SettingsLanguagePropertiesConstants.ON_NOTIFICATION_ENABLED_PROPERTY;

/**
 * UserSettingsChanger implementation that change user's notification settings.
 * This class enable and disable notification
 */
@Service
public class NotificationUserSettingsChanger implements UserSettingsChanger {
    private final UserRepository userRepository;
    private final ResponseMessageResolverDecorator responseMessageResolverDecorator;
    private final Logger logger = LoggerFactory.getLogger(NotificationUserSettingsChanger.class);

    @Autowired
    public NotificationUserSettingsChanger(UserRepository userRepository, ResponseMessageResolverDecorator responseMessageResolverDecorator) {
        this.userRepository = userRepository;
        this.responseMessageResolverDecorator = responseMessageResolverDecorator;
    }

    @Override
    public BotApiMethod<?> change(Update update, SettingsMessage data) {
        String chatId = TelegramUtils.getChatId(update);

        boolean isNotificationEnabled = switchNotificationSettings(chatId);

        String responseMessage = getResponseMessage(update, isNotificationEnabled);
        return new SendMessage(chatId, responseMessage);
    }


    @Override
    public String getName() {
        return "Notification";
    }

    /*
     * Switch user's notification settings to opposite.
     * True to false, false to true
     */
    private boolean switchNotificationSettings(String chatId) {
        User user = this.userRepository.findUserByTelegramId(chatId);
        boolean changedNotificationSettings = !user.getUserSettings().isEnableNotification();
        user.getUserSettings().setEnableNotification(changedNotificationSettings);
        this.userRepository.save(user);
        this.logger.info("Changed user settings with id: {} to: {}", chatId, changedNotificationSettings);
        return changedNotificationSettings;
    }

    /*
        Return response message by user's language
     */
    private String getResponseMessage(Update update, boolean isNotificationEnabled) {
        String responseMessage;

        if (isNotificationEnabled) {
            responseMessage = this.responseMessageResolverDecorator.getResponseMessage(update, ON_NOTIFICATION_ENABLED_PROPERTY);
        } else {
            responseMessage = this.responseMessageResolverDecorator.getResponseMessage(update, ON_NOTIFICATION_DISABLED_PROPERTY);
        }
        return responseMessage;
    }
}
