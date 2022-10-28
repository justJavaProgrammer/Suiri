package com.odeyalo.bot.suiri.service.command.steps.settings.lang;

import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.repository.UserRepository;
import com.odeyalo.bot.suiri.service.command.support.state.settings.ChangeLanguageState;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.LanguageToLanguageCodeConvertor;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import com.odeyalo.bot.suiri.support.lang.UserLanguageChanger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ChangeLanguageChangeUserLanguageSettingsStep implements ChangeUserLanguageSettingsStep {
    private final UserLanguageChanger userLanguageChanger;
    private final UserRepository userRepository;
    private final ResponseMessageResolverDecorator responseMessageResolverDecorator;

    @Autowired
    public ChangeLanguageChangeUserLanguageSettingsStep(UserLanguageChanger userLanguageChanger, UserRepository userRepository, ResponseMessageResolverDecorator responseMessageResolverDecorator) {
        this.userLanguageChanger = userLanguageChanger;
        this.userRepository = userRepository;
        this.responseMessageResolverDecorator = responseMessageResolverDecorator;
    }

    @Override
    public BotApiMethod<?> processStep(Update update, ChangeLanguageMessage message) {
        String language = message.getLanguage();
        User user = userRepository.findUserByTelegramId(message.getUserId());
        String langCode = LanguageToLanguageCodeConvertor.convert(language);
        this.userLanguageChanger.changeUserLanguage(user, langCode);
        String responseMessage = getResponseMessage(update, language);
        return new SendMessage(TelegramUtils.getChatId(update), responseMessage);
    }

    @Override
    public ChangeLanguageState getState() {
        return ChangeLanguageState.CHANGE_LANGUAGE;
    }


    private String getResponseMessage(Update update, String language) {
        return responseMessageResolverDecorator.getResponseMessage(update, ChangeUserLanguageLanguagePropertiesConstants.CHANGE_LANGUAGE) + " " + language;
    }
}
