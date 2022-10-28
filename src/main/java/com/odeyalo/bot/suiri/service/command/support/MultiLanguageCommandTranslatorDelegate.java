package com.odeyalo.bot.suiri.service.command.support;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Delegate command translation to specific LanguageToCommandTranslator
 */
public interface MultiLanguageCommandTranslatorDelegate {

    String getOriginalCommand(Update update);

}
