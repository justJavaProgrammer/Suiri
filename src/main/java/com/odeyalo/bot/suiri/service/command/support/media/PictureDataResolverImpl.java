package com.odeyalo.bot.suiri.service.command.support.media;

import com.odeyalo.bot.suiri.entity.DictionaryItem;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class PictureDataResolverImpl implements PictureDataResolver {
    private static final String GIF_MIME_TYPE_VALUE = "video/mp4";

    @Override
    public PictureData getPictureData(Update update) {
        List<PhotoSize> photos = getPhotos(update);
        if (photos != null) {
            return getPhotoPictureData(update);
        }

        Document document = update.getMessage().getDocument();
        if (document != null) {
            return getGif(document);
        }

        return null;
    }

    private PictureData getGif(Document document) {
        String fileId = document.getFileId();
        String mimeType = document.getMimeType();
        if (isGif(mimeType)) {
            return new PictureData(fileId, DictionaryItem.PictureType.GIF);
        }
        return null;
    }

    private boolean isGif(String mimeType) {
        return MimeType.valueOf(mimeType).equals(MimeType.valueOf(GIF_MIME_TYPE_VALUE));
    }

    private PictureData getPhotoPictureData(Update update) {
        List<PhotoSize> filePath = update.getMessage().getPhoto();
        int index = filePath.size() - 1;
        PhotoSize photoSize = filePath.get(index);
        String fileId = photoSize.getFileId();
        return new PictureData(fileId, DictionaryItem.PictureType.PHOTO);
    }

    private List<PhotoSize> getPhotos(Update update) {
        return update.getMessage().getPhoto();
    }
}
