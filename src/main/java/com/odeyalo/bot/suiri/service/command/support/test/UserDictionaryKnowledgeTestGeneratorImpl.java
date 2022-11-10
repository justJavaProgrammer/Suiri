package com.odeyalo.bot.suiri.service.command.support.test;

import com.odeyalo.bot.suiri.entity.DictionaryItem;
import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.service.command.support.RandomUserWordGetter;
import com.odeyalo.bot.suiri.service.command.support.TestUserKnowledgeLanguagePropertiesConstants;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;


/**
 * Default test generator.
 * This generator support the multi-language message. That means  UserDictionaryKnowledgeTestGeneratorImpl will be generate explanation by user's preferred language
 */
@Service
public class UserDictionaryKnowledgeTestGeneratorImpl implements UserDictionaryKnowledgeTestGenerator {
    private final RandomUserWordGetter randomUserWordGetter;
    private final ResponseMessageResolverHelper responseMessageResolverHelper;

    public static final int OPTIONS_LIMIT = 4;

    @Autowired
    public UserDictionaryKnowledgeTestGeneratorImpl(RandomUserWordGetter randomUserWordGetter,
                                                    ResponseMessageResolverHelper responseMessageResolverHelper) {
        this.randomUserWordGetter = randomUserWordGetter;
        this.responseMessageResolverHelper = responseMessageResolverHelper;
    }

    @Override
    public UserDictionaryKnowledgeTest generateTest(User user) {
        /*
         * Pick a random word from user's dictionary. This word will be used as correct answer
         */
        DictionaryItem correctAnswer = getCorrectAnswerWord(user);

        String picture = correctAnswer.getPicture();
        String originalText = correctAnswer.getOriginalText();
        String translatedText = correctAnswer.getTranslatedText();
        DictionaryItem.PictureType pictureType = correctAnswer.getPictureType();
        String explanation = getExplanation(user, correctAnswer);

        TreeSet<String> options = getOptions(user, OPTIONS_LIMIT, translatedText);

        return UserDictionaryKnowledgeTest
                .builder()
                .originalText(originalText)
                .picture(picture)
                .pictureType(pictureType)
                .correctAnswer(translatedText)
                .explanation(explanation)
                .options(options)
                .build();
    }

    private String getExplanation(User user, DictionaryItem item) {
        String explanation = this.responseMessageResolverHelper.getMessageByLanguageCode(user.getUserSettings().getLanguage(), TestUserKnowledgeLanguagePropertiesConstants.NOT_CORRECT_ANSWER_PROPERTY);
        return explanation + item.getTranslatedText();
    }

    private DictionaryItem getCorrectAnswerWord(User user) {
        return randomUserWordGetter.getRandomWord(user);
    }

    /**
     * Return a Set of options for test
     *
     * @return - set of translated words from user's dictionary
     */

    protected TreeSet<String> getOptions(User user, int limit, String correctAnswer) {
        List<DictionaryItem> dictionaryItems = user.getUserDictionary().getItems();
        /*
         * Stream that returns only UNIQUE elements from user's dictionary and map it to String
         */
        return dictionaryItems.stream()
                .distinct()
                .map(DictionaryItem::getTranslatedText)
                .filter(translatedText -> !translatedText.equals(correctAnswer))
                .limit(limit)
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
