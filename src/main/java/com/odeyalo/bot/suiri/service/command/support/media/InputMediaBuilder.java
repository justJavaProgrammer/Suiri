package com.odeyalo.bot.suiri.service.command.support.media;

import org.telegram.telegrambots.meta.api.objects.media.InputMedia;

/**
 * Build specific input media
 */
public interface InputMediaBuilder {

    /**
     * Build the input media by type
     * @param fileId - file id
     * @return - InputMedia by this type
     */
    InputMedia getInputMedia(String fileId);


    /**
     * What type of input media this builder support
     * @return - type of this builder
     */
    String supportedType();

}
