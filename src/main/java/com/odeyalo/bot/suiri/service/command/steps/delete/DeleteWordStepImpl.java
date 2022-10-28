package com.odeyalo.bot.suiri.service.command.steps.delete;

import com.odeyalo.bot.suiri.domain.DeleteWordMessage;
import com.odeyalo.bot.suiri.entity.Dictionary;
import com.odeyalo.bot.suiri.entity.DictionaryItem;
import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.repository.DictionaryItemRepository;
import com.odeyalo.bot.suiri.repository.UserRepository;
import com.odeyalo.bot.suiri.service.command.support.state.delete.DeleteWordState;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.odeyalo.bot.suiri.service.command.steps.delete.DeleteWordLanguagePropertiesConstants.DELETE_WORD_PROPERTY;

/**
 * Delete the word from user's dictionary
 */
@Service
public class DeleteWordStepImpl implements DeleteWordStep {
    private final DictionaryItemRepository dictionaryItemRepository;
    private final UserRepository userRepository;
    private final ResponseMessageResolverDecorator responseMessageResolverDecorator;

    @Autowired
    public DeleteWordStepImpl(DictionaryItemRepository dictionaryItemRepository, UserRepository userRepository, ResponseMessageResolverDecorator responseMessageResolverDecorator) {
        this.dictionaryItemRepository = dictionaryItemRepository;
        this.userRepository = userRepository;
        this.responseMessageResolverDecorator = responseMessageResolverDecorator;
    }

    @Override
    @Transactional
    public BotApiMethod<?> processStep(Update update, DeleteWordMessage message) {
        Long userId = TelegramUtils.getTelegramUser(update).getId();
        String word = TelegramUtils.getText(update);

        Dictionary userDictionary = deleteWordAndGetDictionary(userId, word);
        String responseMessage = this.responseMessageResolverDecorator.getResponseMessage(update, DELETE_WORD_PROPERTY);
        this.dictionaryItemRepository.deleteDictionaryItemByDictionaryAndOriginalText(userDictionary, word);
        return new SendMessage(TelegramUtils.getChatId(update), responseMessage);
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
