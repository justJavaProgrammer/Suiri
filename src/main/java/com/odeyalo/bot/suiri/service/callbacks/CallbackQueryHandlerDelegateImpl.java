package com.odeyalo.bot.suiri.service.callbacks;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Delegate callback query handling to CallbackQueryHandler by callbackDataName
 */
@Service
public class CallbackQueryHandlerDelegateImpl implements CallbackQueryHandlerDelegate {
    private final Map<String, CallbackQueryHandler> handlers;

    public CallbackQueryHandlerDelegateImpl(List<CallbackQueryHandler> handlersList) {
        this.handlers = handlersList.stream().collect(Collectors.toMap(CallbackQueryHandler::getCallbackDataName, Function.identity()));
    }

    @Override
    public BotApiMethod<?> handleCallbackQuery(CallbackQuery query) {
        return this.handlers.get(query.getData()).handle(query);
    }
}
