package com.odeyalo.bot.suiri.service.callbacks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
public class CallbackQueryHandlerImpl implements CallbackQueryHandler {
    private final Logger logger = LoggerFactory.getLogger(CallbackQueryHandlerImpl.class);

    @Override
    public BotApiMethod<?> handle(CallbackQuery query) {
        Long chatId = query.getMessage().getChatId();
        String id = query.getId();
        this.logger.info("Received: {}", query);
        return new SendMessage(String.valueOf(chatId), "Received query!");
    }
}
