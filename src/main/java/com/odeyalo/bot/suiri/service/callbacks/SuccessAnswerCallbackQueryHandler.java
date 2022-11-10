package com.odeyalo.bot.suiri.service.callbacks;

import com.odeyalo.bot.suiri.repository.UserRepository;
import com.odeyalo.bot.suiri.service.command.support.TestUserKnowledgeLanguagePropertiesConstants;
import com.odeyalo.bot.suiri.service.command.support.media.InputMediaBuilderHelper;
import com.odeyalo.bot.suiri.service.command.support.test.OptionsInlineKeyboardUtils;
import com.odeyalo.bot.suiri.service.command.support.test.Questions;
import com.odeyalo.bot.suiri.service.command.support.test.UserDictionaryKnowledgeTest;
import com.odeyalo.bot.suiri.service.command.support.test.store.UserKnowledgeTestingStore;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverHelper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

import static java.lang.Math.toIntExact;

/**
 * Callback handler that handles "success" callback name and sends next question
 */
@Service
public class SuccessAnswerCallbackQueryHandler implements CallbackQueryHandler {
    private final AbsSender absSender;
    private final UserKnowledgeTestingStore store;
    private final ResponseMessageResolverHelper helper;
    private final UserRepository userRepository;
    private final InputMediaBuilderHelper inputMediaBuilderHelper;
    private static final String CALLBACK_NAME = "success";
    private final Logger logger = LoggerFactory.getLogger(SuccessAnswerCallbackQueryHandler.class);

    @Autowired
    public SuccessAnswerCallbackQueryHandler(AbsSender absSender, UserKnowledgeTestingStore store, ResponseMessageResolverHelper helper, UserRepository userRepository, InputMediaBuilderHelper inputMediaBuilderHelper) {
        this.absSender = absSender;
        this.store = store;
        this.helper = helper;
        this.userRepository = userRepository;
        this.inputMediaBuilderHelper = inputMediaBuilderHelper;
    }

    @Override
    @SneakyThrows
    public BotApiMethod<?> handle(CallbackQuery query) {
        long messageId = query.getMessage().getMessageId();
        String chatId = String.valueOf(query.getMessage().getChatId());
        Questions questions = store.getById(chatId);

        updateQuestionsScore(chatId, questions);

        UserDictionaryKnowledgeTest next = questions.poll();

        String language = getUserLanguage(query);

        if (next == null) {
            clearStore(chatId);
            int correctAnswersScore = questions.getCorrectAnswersScore();
            String responseMessage = getResponseMessage(questions, language, correctAnswersScore);
            return new SendMessage(chatId, responseMessage);
        }

        String message = this.helper.getMessageByLanguageCode(language, TestUserKnowledgeLanguagePropertiesConstants.ON_START_PROPERTY);

        InlineKeyboardMarkup keyboardMarkup = getNextQuestionKeyboard(next);
        InputMedia media = getInputMedia(next, message);

        EditMessageMedia build = getEditMessageMedia(messageId, chatId, keyboardMarkup, media);
        this.absSender.execute(build);

        // Send the empty SendMessage, since Telegram API does not support EditMessageMedia sending using BotApiMethod
        return new SendMessage("", "");
    }

    private String getResponseMessage(Questions questions, String language, int correctAnswersScore) {
        String message = this.helper.getMessageByLanguageCode(language, TestUserKnowledgeLanguagePropertiesConstants.FINISH_CORRECT_ANSWERS_SCORE);
        return new StringBuilder(message)
                .append(" ")
                .append(correctAnswersScore)
                .append(" / ")
                .append(questions.getQuestionsSize())
                .toString();
    }

    private void clearStore(String chatId) {
        this.store.remove(chatId);
    }

    private void updateQuestionsScore(String chatId, Questions questions) {
        questions.incrementCorrectAnswers();
        store.save(chatId, questions);
    }

    @Override
    public String getCallbackDataName() {
        return CALLBACK_NAME;
    }

    private InlineKeyboardMarkup getNextQuestionKeyboard(UserDictionaryKnowledgeTest test) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = OptionsInlineKeyboardUtils.getKeyboard(test);
        // Set the keyboard to the markup
        markupInline.setKeyboard(keyboard);
        return markupInline;
    }

    private EditMessageMedia getEditMessageMedia(long messageId, String chatId, InlineKeyboardMarkup keyboardMarkup, InputMedia media) {
        return EditMessageMedia.builder()
                .chatId(chatId)
                .messageId(toIntExact(messageId))
                .media(media)
                .replyMarkup(keyboardMarkup)
                .build();
    }

    private InputMedia getInputMedia(UserDictionaryKnowledgeTest next, String message) {
        InputMedia inputMedia = this.inputMediaBuilderHelper.getInputMedia(next.getPictureType().name(), next.getPicture());
        inputMedia.setCaption(message + next.getOriginalText());
        return inputMedia;
    }

    private String getUserLanguage(CallbackQuery query) {
        return userRepository.findUserByTelegramId(String.valueOf(query.getMessage().getChatId())).getUserSettings().getLanguage();
    }
}
