package com.odeyalo.bot.suiri.service.command.support.media.sender;

import com.odeyalo.bot.suiri.service.command.support.media.InputMediaBuilderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DefaultInputMediaSenderDelegate implements InputMediaSenderDelegate {
    private final InputMediaBuilderHelper builderHelper;
    /**
     * Key - sender type
     * Value - InputMediaSender
     */
    private final Map<String, InputMediaSender> senders;

    @Autowired
    public DefaultInputMediaSenderDelegate(InputMediaBuilderHelper builderHelper, List<InputMediaSender> senders) {
        this.builderHelper = builderHelper;
        this.senders = senders.stream().collect(Collectors.toMap(InputMediaSender::senderType, Function.identity()));
    }

    @Override
    public void sendMedia(String chatId, String picture, String pictureType, InlineKeyboardMarkup markupInline, String caption) {
        InputMedia inputMedia = builderHelper.getInputMedia(pictureType, picture);
        if (senders.containsKey(pictureType)) {
            senders.get(pictureType).send(chatId, inputMedia, markupInline, caption);
        }
    }
}
