package com.odeyalo.bot.suiri.service.command.support.translate;

/**
 * Abstract TranslatedWordSuggester implementation that uses Yandex API to translate words
 * This class is abstract since we can send http request in different ways
 */
public interface AbstractYandexAPITranslatedWordSuggester extends TranslatedWordSuggester {}
