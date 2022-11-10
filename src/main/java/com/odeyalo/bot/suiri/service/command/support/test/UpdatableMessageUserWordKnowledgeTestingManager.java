package com.odeyalo.bot.suiri.service.command.support.test;

import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.entity.UserSettings;
import com.odeyalo.bot.suiri.service.command.support.media.InputMediaBuilderHelper;
import com.odeyalo.bot.suiri.service.command.support.media.sender.InputMediaSenderDelegate;
import com.odeyalo.bot.suiri.service.command.support.test.store.UserKnowledgeTestingStore;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.List;

import static com.odeyalo.bot.suiri.service.command.support.TestUserKnowledgeLanguagePropertiesConstants.NOT_ENOUGH_WORDS_EXCEPTION_MESSAGE_PROPERTY;
import static com.odeyalo.bot.suiri.service.command.support.TestUserKnowledgeLanguagePropertiesConstants.ON_START_PROPERTY;
import static com.odeyalo.bot.suiri.service.command.support.test.OptionsInlineKeyboardUtils.getKeyboard;

/**
 * Generate test for user and send it using updatable message and inline keyboard
 */
@Service
public class UpdatableMessageUserWordKnowledgeTestingManager extends AbstractUserWordKnowledgeTestingManager {
    private final MultiQuestionsUserDictionaryKnowledgeGenerator generator;
    private final UserKnowledgeTestingStore store;
    private final InputMediaSenderDelegate senderDelegate;
    @Autowired
    public UpdatableMessageUserWordKnowledgeTestingManager(UserDictionaryKnowledgeTestGenerator userDictionaryKnowledgeTestGenerator,
                                                           ResponseMessageResolverDecorator responseMessageResolverDecorator,
                                                           AbsSender absSender,
                                                           MultiQuestionsUserDictionaryKnowledgeGenerator generator,
                                                           UserKnowledgeTestingStore store, InputMediaBuilderHelper builderHelper, InputMediaSenderDelegate senderDelegate) {
        super(userDictionaryKnowledgeTestGenerator, responseMessageResolverDecorator, absSender, builderHelper);
        this.generator = generator;
        this.store = store;
        this.senderDelegate = senderDelegate;
    }

    @Override
    public PartialBotApiMethod<?> getUserKnowledgeTest(Update update, User user) {
        String chatId = TelegramUtils.getChatId(update);

        if (!checkWords(user)) {
            String message = responseMessageResolverDecorator.getResponseMessage(update, NOT_ENOUGH_WORDS_EXCEPTION_MESSAGE_PROPERTY);
            return new SendMessage(chatId, message);
        }

        List<UserDictionaryKnowledgeTest> tests = generator.generateTests(user, 10);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        Questions questions = new Questions(tests);
        UserDictionaryKnowledgeTest firstQuestion = questions.poll();

        ArrayList<List<InlineKeyboardButton>> keyboard = getKeyboard(firstQuestion);

        markupInline.setKeyboard(keyboard);

        // Save the questions to questions store to get them from everywhere
        this.store.save(chatId, questions);

        String caption = getResponseMessageByUserLanguage(update, ON_START_PROPERTY) + firstQuestion.getOriginalText();

        sendPhotoOrGif(chatId, markupInline, firstQuestion, caption);
        return new SendMessage("", "");
    }

    @Override
    public UserSettings.PreferredKnowledgeTestType getType() {
        return UserSettings.PreferredKnowledgeTestType.UPDATABLE_MESSAGE;
    }

    @SneakyThrows
    private void sendPhotoOrGif(String chatId, InlineKeyboardMarkup markupInline, UserDictionaryKnowledgeTest firstQuestion, String caption) {
        senderDelegate.sendMedia(chatId, firstQuestion.getPicture(), firstQuestion.getPictureType().name(), markupInline, caption);
    }
}
