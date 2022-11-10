package com.odeyalo.bot.suiri.service.command.support.media;

import org.telegram.telegrambots.meta.api.objects.media.InputMedia;

/**
 * Helper that helps to build the InputMedia by type.
 */
public interface InputMediaBuilderHelper {

    /**
     * Build input media and return it
     * @param type - type of file
     * @param fileId - file id
     * @return - InputMedia or null
     */
    InputMedia getInputMedia(String type, String fileId);

}
