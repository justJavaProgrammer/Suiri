package com.odeyalo.bot.suiri.service.callbacks;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackQueryHandlerDelegate {

    void handleCallbackQuery(CallbackQuery query);

}
