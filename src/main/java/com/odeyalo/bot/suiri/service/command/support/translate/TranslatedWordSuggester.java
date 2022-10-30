package com.odeyalo.bot.suiri.service.command.support.translate;

import java.util.Set;

/**
 * Interface that help to suggest translated words
 */
public interface TranslatedWordSuggester {
    /**
     * Default number of suggestion
     */
    Integer SUGGEST_NUMBER_SIZE = 3;

    /**
     * Suggest words by original word with default size of suggestion
     * @param originalWord - original word to translate
     * @param requiredLanguage - language to translate
     * @return - Set of suggested words. Never null
     */
    default Set<String> suggestWords(String originalWord, String requiredLanguage) {
        return suggestWords(originalWord, requiredLanguage, SUGGEST_NUMBER_SIZE);
    }

    /**
     * Suggest words by original word
     * @param originalWord - original word that will be translated
     * @param requiredLanguage - language to translate
     * @param size - size of suggestion
     * @return - set of suggested words. Never null
     */
    Set<String> suggestWords(String originalWord, String requiredLanguage, Integer size);
}
