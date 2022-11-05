package com.odeyalo.bot.suiri.service.command.support.test;

import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Add additional support methods for child classes
 * @see com.odeyalo.bot.suiri.service.command.support.test.UserWordKnowledgeTestingManager
 * @see com.odeyalo.bot.suiri.service.command.support.test.QuizPollUserWordKnowledgeTestingManager
 */
public abstract class AbstractUserWordKnowledgeTestingManager implements UserWordKnowledgeTestingManager {
    protected final UserDictionaryKnowledgeTestGenerator userDictionaryKnowledgeTestGenerator;
    protected final ResponseMessageResolverDecorator responseMessageResolverDecorator;
    protected final AbsSender absSender;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected AbstractUserWordKnowledgeTestingManager(UserDictionaryKnowledgeTestGenerator userDictionaryKnowledgeTestGenerator,
                                                      ResponseMessageResolverDecorator responseMessageResolverDecorator,
                                                      AbsSender absSender) {
        this.userDictionaryKnowledgeTestGenerator = userDictionaryKnowledgeTestGenerator;
        this.responseMessageResolverDecorator = responseMessageResolverDecorator;
        this.absSender = absSender;
    }

    /**
     * Return a response message by user's preferred language and property
     * @param update - current update
     * @param property - property to get text by language
     * @return - response message by user's preferred language
     */
    protected String getResponseMessageByUserLanguage(Update update, String property) {
        return this.responseMessageResolverDecorator.getResponseMessage(update, property);
    }
}
