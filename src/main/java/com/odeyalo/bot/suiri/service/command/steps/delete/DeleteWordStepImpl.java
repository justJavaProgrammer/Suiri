package com.odeyalo.bot.suiri.service.command.steps.delete;

import com.odeyalo.bot.suiri.domain.DeleteWordMessage;
import com.odeyalo.bot.suiri.entity.Dictionary;
import com.odeyalo.bot.suiri.entity.DictionaryItem;
import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.repository.DictionaryItemRepository;
import com.odeyalo.bot.suiri.repository.DictionaryRepository;
import com.odeyalo.bot.suiri.repository.UserRepository;
import com.odeyalo.bot.suiri.service.command.support.state.delete.DeleteWordState;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Delete the word from user's dictionary
 */
@Service
public class DeleteWordStepImpl implements DeleteWordStep {
    private final DictionaryItemRepository dictionaryItemRepository;
    private final UserRepository userRepository;

    @Autowired
    public DeleteWordStepImpl(DictionaryItemRepository dictionaryItemRepository, UserRepository userRepository) {
        this.dictionaryItemRepository = dictionaryItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public BotApiMethod<?> processStep(Update update, DeleteWordMessage message) {
        Long userId = TelegramUtils.getTelegramUser(update).getId();
        String word = TelegramUtils.getText(update);

        Dictionary userDictionary = deleteWordAndGetDictionary(userId, word);

        this.dictionaryItemRepository.deleteDictionaryItemByDictionaryAndOriginalText(userDictionary, word);
        return new SendMessage(TelegramUtils.getChatId(update), String.format("The word: %s was successful deleted!", word));
    }

    @Override
    public DeleteWordState getState() {
        return DeleteWordState.DELETE_WORD;
    }

    private Dictionary deleteWordAndGetDictionary(Long userId, String word) {
        User user = userRepository.findUserByTelegramId(String.valueOf(userId));
        Dictionary userDictionary = user.getUserDictionary();
        DictionaryItem item = this.dictionaryItemRepository.findDictionaryItemByOriginalTextAndDictionary(word, userDictionary);
        userDictionary.delete(item);
        return userDictionary;
    }
}
