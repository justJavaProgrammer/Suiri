package com.odeyalo.bot.suiri.service.command.support.media;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface PictureDataResolver {

    /**
     * Resolve data about picture from telegram API. If no picture was provided null will be returned
     * @param update - telegram update
     * @return - data about picture. Null otherwise
     */
    PictureData getPictureData(Update update);

}
