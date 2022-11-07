package com.odeyalo.bot.suiri.service.callbacks;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackQueryHandler {

    /**
     * Handle callback query from telegram
     * @param query - telegram callback query
     * @return - BotApiMethod to return. Can be null
     */
    BotApiMethod<?> handle(CallbackQuery query);

    /**
     * @return - the name of the callback data that this handler handles
     */
    String getCallbackDataName();
}
