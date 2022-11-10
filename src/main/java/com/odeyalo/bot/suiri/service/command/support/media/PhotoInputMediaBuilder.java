package com.odeyalo.bot.suiri.service.command.support.media;

import com.odeyalo.bot.suiri.entity.DictionaryItem;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

/**
 * Builder to build InputMediaPhoto
 */
@Component
public class PhotoInputMediaBuilder implements InputMediaBuilder {

    @Override
    public InputMedia getInputMedia(String fileId) {
        return InputMediaPhoto.builder().media(fileId).build();
    }

    @Override
    public String supportedType() {
        return DictionaryItem.PictureType.PHOTO.name();
    }
}
