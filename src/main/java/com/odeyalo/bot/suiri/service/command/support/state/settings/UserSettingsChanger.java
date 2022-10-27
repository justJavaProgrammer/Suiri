package com.odeyalo.bot.suiri.service.command.support.state.settings;

import com.odeyalo.bot.suiri.domain.SettingsMessage;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UserSettingsChanger {


    BotApiMethod<?> change(Update update, SettingsMessage data);

    /**
     * What type of user's settings this changer is handle
     * Returns never null
     * @return - type of user's settings for changer
     */
    String getName();
}
