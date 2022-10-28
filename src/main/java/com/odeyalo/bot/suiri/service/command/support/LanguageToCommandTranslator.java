package com.odeyalo.bot.suiri.service.command.support;

/**
 * Interface to translate language that different from original command to specific command and return it.
 */
public interface LanguageToCommandTranslator {
    /**
     *
     * @param originalText - original text from message
     * @return - command that exist, null otherwise
     */
    String translateTo(String originalText);

    /**
     * What language this translator is support
     * @return - language that this translator is support
     */
    String supportedLanguage();
}
