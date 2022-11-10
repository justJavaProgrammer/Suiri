package com.odeyalo.bot.suiri.service.command.support.media.sender;

import com.odeyalo.bot.suiri.entity.DictionaryItem;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Use this method to send photo to user
 */
@Component
public class PhotoInputMediaSender implements InputMediaSender {
    private final AbsSender absSender;

    @Autowired
    public PhotoInputMediaSender(AbsSender absSender) {
        this.absSender = absSender;
    }

    @Override
    @SneakyThrows
    public void send(String chatId, InputMedia media) {
        SendPhoto build = SendPhoto.builder().photo(new InputFile(media.getMedia())).chatId(chatId).build();
        absSender.execute(build);
    }

    @Override
    @SneakyThrows
    public <T extends PartialBotApiMethod<Message>> void send(T message) {
        if ((message instanceof SendPhoto)) {
            SendPhoto photo = (SendPhoto) message;
            absSender.execute(photo);
        }
    }

    @Override
    @SneakyThrows
    public void send(String chatId, InputMedia media, InlineKeyboardMarkup markupInline, String caption) {
        SendPhoto build = SendPhoto.builder().chatId(chatId).photo(new InputFile(media.getMedia())).caption(caption).replyMarkup(markupInline).build();
        absSender.execute(build);
    }

    @Override
    public String senderType() {
        return DictionaryItem.PictureType.PHOTO.name();
    }
}
