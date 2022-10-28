package com.odeyalo.bot.suiri.service.command.steps.delete;

import com.odeyalo.bot.suiri.domain.DeleteWordMessage;
import com.odeyalo.bot.suiri.service.command.support.state.delete.DeleteWordState;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static com.odeyalo.bot.suiri.service.command.steps.delete.DeleteWordLanguagePropertiesConstants.START_PROPERTY;

@Service
public class StartDeleteWordStep implements DeleteWordStep {
    private final ResponseMessageResolverDecorator responseMessageResolverDecorator;

    @Autowired
    public StartDeleteWordStep(ResponseMessageResolverDecorator responseMessageResolverDecorator) {
        this.responseMessageResolverDecorator = responseMessageResolverDecorator;
    }

    @Override
    public BotApiMethod<?> processStep(Update update, DeleteWordMessage message) {
        User user = TelegramUtils.getTelegramUser(update);
        message.setUserId(String.valueOf(user.getId()));
        this.responseMessageResolverDecorator.getResponseMessage(update, START_PROPERTY);
        return new SendMessage(TelegramUtils.getChatId(update), "Send the word that you want to delete");
    }

    @Override
    public DeleteWordState getState() {
        return DeleteWordState.START;
    }
}
