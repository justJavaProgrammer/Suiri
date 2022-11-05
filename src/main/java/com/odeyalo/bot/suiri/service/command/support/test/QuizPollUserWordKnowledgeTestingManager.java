package com.odeyalo.bot.suiri.service.command.support.test;

import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.service.command.support.QuizPollBuilderHelper;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.SortedSet;

import static com.odeyalo.bot.suiri.service.command.support.TestUserKnowledgeLanguagePropertiesConstants.ON_START_PROPERTY;

/**
 * Generate test and send it as quiz poll
 */
@Service
public class QuizPollUserWordKnowledgeTestingManager extends AbstractUserWordKnowledgeTestingManager {
    private final QuizPollBuilderHelper quizPollBuilderHelper;

    @Autowired
    public QuizPollUserWordKnowledgeTestingManager(AbsSender absSender,
                                                   QuizPollBuilderHelper quizPollBuilderHelper,
                                                   ResponseMessageResolverDecorator responseMessageResolverDecorator,
                                                   UserDictionaryKnowledgeTestGenerator userDictionaryKnowledgeTestGenerator) {
        super(userDictionaryKnowledgeTestGenerator, responseMessageResolverDecorator, absSender);
        this.quizPollBuilderHelper = quizPollBuilderHelper;
    }

    @Override
    public BotApiMethod<?> getUserKnowledgeTest(Update update, User user) {
        String chatId = TelegramUtils.getChatId(update);
        UserDictionaryKnowledgeTest test = userDictionaryKnowledgeTestGenerator.generateTest(user);

        sendWordPhoto(chatId, test.getPicture());

        return getPoll(chatId, test.getOriginalText(), test.getExplanation(), test.getCorrectAnswer(), update, test.getOptions());
    }

    @SneakyThrows
    private void sendWordPhoto(String chatId, String picture) {
        SendPhoto sendPhoto = SendPhoto.builder()
                .photo(new InputFile(picture))
                .chatId(chatId)
                .build();
        absSender.execute(sendPhoto);
    }

    private SendPoll getPoll(String chatId, String originalText, String explanation, String correctAnswer, Update update, SortedSet<String> options) {
        String questionText = getResponseMessageByUserLanguage(update, ON_START_PROPERTY);
        return this.quizPollBuilderHelper.createPoll(questionText + originalText,
                chatId,
                explanation,
                correctAnswer, options, QuizPollBuilderHelper.DEFAULT_LIMIT);
    }

}
