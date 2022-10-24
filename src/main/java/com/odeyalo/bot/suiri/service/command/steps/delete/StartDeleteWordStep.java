package com.odeyalo.bot.suiri.service.command.steps.delete;

import com.odeyalo.bot.suiri.domain.DeleteWordMessage;
import com.odeyalo.bot.suiri.service.command.support.state.delete.DeleteWordState;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class StartDeleteWordStep implements DeleteWordStep {

    @Override
    public BotApiMethod<?> processStep(Update update, DeleteWordMessage message) {
        User user = TelegramUtils.getTelegramUser(update);
        message.setUserId(String.valueOf(user.getId()));
        return new SendMessage(TelegramUtils.getChatId(update), "Send the word that you want to delete");
    }

    @Override
    public DeleteWordState getState() {
        return DeleteWordState.START;
    }
}
