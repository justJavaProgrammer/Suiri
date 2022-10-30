package com.odeyalo.bot.suiri.service.command.support.translate;

import com.odeyalo.bot.suiri.support.lang.Languages;

import java.util.Set;

/**
 * TranslateRequestExceptionHandler implementation that add logic to parent interface.
 * This implementation returns suggestion list from another service
 */
public interface SuggestWordTranslateRequestExceptionHandler extends TranslateRequestExceptionHandler {

    Set<String> suggestWords(String word, String fromLanguage, String toLanguage);

}
