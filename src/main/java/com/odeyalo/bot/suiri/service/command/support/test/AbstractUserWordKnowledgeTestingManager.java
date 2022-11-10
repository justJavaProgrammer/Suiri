package com.odeyalo.bot.suiri.service.command.support.test;

import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.service.command.support.media.InputMediaBuilderHelper;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Add additional support methods for child classes
 *
 * @see com.odeyalo.bot.suiri.service.command.support.test.UserWordKnowledgeTestingManager
 * @see com.odeyalo.bot.suiri.service.command.support.test.QuizPollUserWordKnowledgeTestingManager
 */
public abstract class AbstractUserWordKnowledgeTestingManager implements UserWordKnowledgeTestingManager {
    protected final UserDictionaryKnowledgeTestGenerator userDictionaryKnowledgeTestGenerator;
    protected final ResponseMessageResolverDecorator responseMessageResolverDecorator;
    protected final AbsSender absSender;
    protected final InputMediaBuilderHelper builderHelper;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected AbstractUserWordKnowledgeTestingManager(UserDictionaryKnowledgeTestGenerator userDictionaryKnowledgeTestGenerator,
                                                      ResponseMessageResolverDecorator responseMessageResolverDecorator,
                                                      AbsSender absSender, InputMediaBuilderHelper builderHelper) {
        this.userDictionaryKnowledgeTestGenerator = userDictionaryKnowledgeTestGenerator;
        this.responseMessageResolverDecorator = responseMessageResolverDecorator;
        this.absSender = absSender;
        this.builderHelper = builderHelper;
    }

    /**
     * @param user - user dictionary to check
     * @return - true if user has enough words to create test
     */
    protected boolean checkWords(User user) {
        return user.getUserDictionary().getItems().size() > 2;
    }

    /**
     * Send animation(GIF or H.264/MPEG-4 AVC video without sound)
     * @param chatId - chat id to send photo
     * @param fileId - file to send
     */
    @SneakyThrows
    protected void sendAnimation(String chatId, String fileId) {
        SendAnimation animation = new SendAnimation(chatId, new InputFile(fileId));
        absSender.execute(animation);
    }

    /**
     * Send the photo to specific chat id
     * @param chatId - chat id to send photo
     * @param fileId - file to send
     */
    @SneakyThrows
    protected void sendPhoto(String chatId, String fileId) {
        SendPhoto photo = new SendPhoto(chatId, new InputFile(fileId));
        absSender.execute(photo);
    }

    /**
     * Return a response message by user's preferred language and property
     *
     * @param update   - current update
     * @param property - property to get text by language
     * @return - response message by user's preferred language
     */
    protected String getResponseMessageByUserLanguage(Update update, String property) {
        return this.responseMessageResolverDecorator.getResponseMessage(update, property);
    }
}
