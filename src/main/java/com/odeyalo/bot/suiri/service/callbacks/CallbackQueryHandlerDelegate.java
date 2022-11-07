package com.odeyalo.bot.suiri.service.callbacks;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


/**
 * Delegate  callback query handling to other CallbackQueryHandler
 */
public interface CallbackQueryHandlerDelegate {

    BotApiMethod<?> handleCallbackQuery(CallbackQuery query);

}
