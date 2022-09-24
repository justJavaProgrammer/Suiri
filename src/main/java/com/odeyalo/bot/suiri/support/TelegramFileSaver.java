package com.odeyalo.bot.suiri.support;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Save file to system. Download file, if needed
 */
public interface TelegramFileSaver {
    /**
     *
     * @param fileId - file id to download
     * @return - file path
     * @throws TelegramApiException - if exception was occurred
     */
    String save(String fileId) throws TelegramApiException;
}
