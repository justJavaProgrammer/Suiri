package com.odeyalo.bot.suiri.service.command.support.test;

import com.odeyalo.bot.suiri.entity.DictionaryItem;
import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.entity.UserSettings;
import com.odeyalo.bot.suiri.service.command.support.QuizPollBuilderHelper;
import com.odeyalo.bot.suiri.service.command.support.media.InputMediaBuilderHelper;
import com.odeyalo.bot.suiri.service.command.support.media.sender.InputMediaSenderDelegate;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.SortedSet;

import static com.odeyalo.bot.suiri.service.command.support.TestUserKnowledgeLanguagePropertiesConstants.NOT_ENOUGH_WORDS_EXCEPTION_MESSAGE_PROPERTY;
import static com.odeyalo.bot.suiri.service.command.support.TestUserKnowledgeLanguagePropertiesConstants.ON_START_PROPERTY;

/**
 * Generate test and send it as quiz poll
 */
@Service
public class QuizPollUserWordKnowledgeTestingManager extends AbstractUserWordKnowledgeTestingManager {
    private final QuizPollBuilderHelper quizPollBuilderHelper;
    private final InputMediaSenderDelegate senderDelegate;

    @Autowired
    public QuizPollUserWordKnowledgeTestingManager(AbsSender absSender,
                                                   QuizPollBuilderHelper quizPollBuilderHelper,
                                                   ResponseMessageResolverDecorator responseMessageResolverDecorator,
                                                   UserDictionaryKnowledgeTestGenerator userDictionaryKnowledgeTestGenerator, InputMediaBuilderHelper builderHelper, InputMediaSenderDelegate senderDelegate) {
        super(userDictionaryKnowledgeTestGenerator, responseMessageResolverDecorator, absSender, builderHelper);
        this.quizPollBuilderHelper = quizPollBuilderHelper;
        this.senderDelegate = senderDelegate;
    }

    @Override
    public BotApiMethod<?> getUserKnowledgeTest(Update update, User user) {
        String chatId = TelegramUtils.getChatId(update);

        if (!checkWords(user)) {
            String message = responseMessageResolverDecorator.getResponseMessage(update, NOT_ENOUGH_WORDS_EXCEPTION_MESSAGE_PROPERTY);
            return new SendMessage(chatId, message);
        }

        UserDictionaryKnowledgeTest test = userDictionaryKnowledgeTestGenerator.generateTest(user);

        sendByPictureType(chatId, test.getPictureType(), test.getPicture());

        return getPoll(chatId, test.getOriginalText(), test.getExplanation(), test.getCorrectAnswer(), update, test.getOptions());
    }

    private void sendByPictureType(String chatId, DictionaryItem.PictureType pictureType, String picture) {
        this.senderDelegate.sendMedia(chatId, picture, pictureType.name());
    }

    @Override
    public UserSettings.PreferredKnowledgeTestType getType() {
        return UserSettings.PreferredKnowledgeTestType.QUIZ_POLL;
    }

    private SendPoll getPoll(String chatId, String originalText, String explanation, String correctAnswer, Update update, SortedSet<String> options) {
        String questionText = getResponseMessageByUserLanguage(update, ON_START_PROPERTY);
        return this.quizPollBuilderHelper.createPoll(questionText + originalText,
                chatId,
                explanation,
                correctAnswer, options, QuizPollBuilderHelper.DEFAULT_LIMIT);
    }

}
