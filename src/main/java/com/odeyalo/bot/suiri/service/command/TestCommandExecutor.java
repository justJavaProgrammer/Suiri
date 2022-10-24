package com.odeyalo.bot.suiri.service.command;

import com.odeyalo.bot.suiri.entity.Dictionary;
import com.odeyalo.bot.suiri.entity.DictionaryItem;
import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.repository.UserRepository;
import com.odeyalo.bot.suiri.service.command.support.QuizPollBuilderHelper;
import com.odeyalo.bot.suiri.service.command.support.RandomUserWordGetter;
import com.odeyalo.bot.suiri.service.sender.TelegramPhotoSender;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class TestCommandExecutor implements CommandExecutor {
    private static final String COMMAND_NAME = "/test";
    private final UserRepository userRepository;
    private final TelegramPhotoSender photoSender;
    private final QuizPollBuilderHelper quizPollBuilderHelper;
    private final RandomUserWordGetter randomUserWordGetter;
    private static final String DEFAULT_QUESTION_TEXT_VALUE_FORMAT = "Translate this word: %s";
    private static final String DEFAULT_EXPLANATION_TEXT_VALUE_FORMAT = "The correct answer is: %s";

    private final Logger logger = LoggerFactory.getLogger(TestCommandExecutor.class);

    @Autowired
    public TestCommandExecutor(UserRepository userRepository, TelegramPhotoSender photoSender, QuizPollBuilderHelper quizPollBuilderHelper, RandomUserWordGetter randomUserWordGetter) {
        this.userRepository = userRepository;
        this.photoSender = photoSender;
        this.quizPollBuilderHelper = quizPollBuilderHelper;
        this.randomUserWordGetter = randomUserWordGetter;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        Long telegramId = TelegramUtils.getTelegramUser(update).getId();
        String chatId = TelegramUtils.getChatId(update);

        Dictionary userDictionary = getDictionary(telegramId);

        List<DictionaryItem> dictionaryItems = userDictionary.getItems();

        if (dictionaryItems == null || dictionaryItems.size() < 2) {
            return new SendMessage(chatId, "You don't have enough words to create a quiz. Add new words using: /add command");
        }
        /*
         * Pick a random word from user's dictionary
         */
        Long userId = userDictionary.getUser().getId();
        DictionaryItem dictionaryItem = randomUserWordGetter.getRandomWord(String.valueOf(userId));
        String picture = dictionaryItem.getPicture();
        String originalText = dictionaryItem.getOriginalText();
        String translatedText = dictionaryItem.getTranslatedText();

        sendWordPhoto(chatId, picture);

        TreeSet<String> options = dictionaryItems.stream().map(DictionaryItem::getTranslatedText).collect(Collectors.toCollection(TreeSet::new));

        SendPoll poll = getPoll(chatId, originalText, translatedText, options);
        this.logger.info("Created poll: {}", poll);
        return poll;
    }

    private Dictionary getDictionary(Long telegramId) {
        User user = this.userRepository.findUserByTelegramId(String.valueOf(telegramId));
        return user.getUserDictionary();
    }

    private SendPoll getPoll(String chatId, String originalText, String translatedText, TreeSet<String> options) {
        return this.quizPollBuilderHelper.createPoll(String.format(DEFAULT_QUESTION_TEXT_VALUE_FORMAT,
                originalText),
                chatId,
                String.format(DEFAULT_EXPLANATION_TEXT_VALUE_FORMAT, translatedText),
                translatedText, options, QuizPollBuilderHelper.DEFAULT_LIMIT);
    }

    private void sendWordPhoto(String chatId, String picture) {
        try {
            SendPhoto sendPhoto = SendPhoto.builder()
                    .photo(new InputFile(picture))
                    .chatId(chatId)
                    .build();
            photoSender.sendPhoto(sendPhoto);
        } catch (TelegramApiException exception) {
            this.logger.error("Failed to send photo ", exception);
        }
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public boolean isMultiStepCommand() {
        return false;
    }
}
