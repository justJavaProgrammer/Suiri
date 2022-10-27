package com.odeyalo.bot.suiri.service.command.steps.settings.lang;

import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.repository.UserRepository;
import com.odeyalo.bot.suiri.service.command.support.state.settings.ChangeLanguageState;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.LanguageToLanguageCodeConvertor;
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

    @Autowired
    public ChangeLanguageChangeUserLanguageSettingsStep(UserLanguageChanger userLanguageChanger, UserRepository userRepository) {
        this.userLanguageChanger = userLanguageChanger;
        this.userRepository = userRepository;
    }

    @Override
    public BotApiMethod<?> processStep(Update update, ChangeLanguageMessage message) {
        String language = message.getLanguage();
        User user = userRepository.findUserByTelegramId(message.getUserId());
        String langCode = LanguageToLanguageCodeConvertor.convert(language);
        this.userLanguageChanger.changeUserLanguage(user, langCode);
        return new SendMessage(TelegramUtils.getChatId(update), "Changed language to: " + language);
    }

    @Override
    public ChangeLanguageState getState() {
        return ChangeLanguageState.CHANGE_LANGUAGE;
    }
}
