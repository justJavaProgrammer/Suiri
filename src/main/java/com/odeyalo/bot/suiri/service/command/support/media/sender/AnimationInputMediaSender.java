package com.odeyalo.bot.suiri.service.command.support.media.sender;

import com.odeyalo.bot.suiri.entity.DictionaryItem;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Use this method to send animation files (GIF or H.264/MPEG-4 AVC video without sound)
 */
@Component
public class AnimationInputMediaSender implements InputMediaSender {
    private final AbsSender absSender;

    @Autowired
    public AnimationInputMediaSender(AbsSender absSender) {
        this.absSender = absSender;
    }

    @Override
    @SneakyThrows
    public void send(String chatId, InputMedia media) {
        SendAnimation build = SendAnimation.builder().animation(new InputFile(media.getMedia())).chatId(chatId).build();
        absSender.execute(build);
    }

    @Override
    @SneakyThrows
    public void send(String chatId, InputMedia media, InlineKeyboardMarkup markupInline, String caption) {
        SendAnimation build = SendAnimation.builder().chatId(chatId).animation(new InputFile(media.getMedia())).caption(caption).replyMarkup(markupInline).build();
        absSender.execute(build);
    }

    @Override
    @SneakyThrows
    public <T extends PartialBotApiMethod<Message>> void send(T message) {
        if ((message instanceof SendAnimation)) {
            SendAnimation animation = (SendAnimation) message;
            absSender.execute(animation);
        }
    }

    @Override
    public String senderType() {
        return DictionaryItem.PictureType.GIF.name();
    }
}
