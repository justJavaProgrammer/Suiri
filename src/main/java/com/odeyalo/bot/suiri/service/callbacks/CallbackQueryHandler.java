package com.odeyalo.bot.suiri.service.callbacks;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@FunctionalInterface
public interface CallbackQueryHandler {

    BotApiMethod<?> handle(CallbackQuery query);

}
