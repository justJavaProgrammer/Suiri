package com.odeyalo.bot.suiri.service.command.support.media;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class InputMediaBuilderHelperImpl implements InputMediaBuilderHelper {
    /**
     * Key - builder type
     * Value - InputMediaBuilder
     */
    private final Map<String, InputMediaBuilder> builders;

    public InputMediaBuilderHelperImpl(List<InputMediaBuilder> builders) {
        this.builders = builders.stream().collect(Collectors.toMap(InputMediaBuilder::supportedType, Function.identity()));
    }

    @Override
    public InputMedia getInputMedia(String type, String fileId) {
        InputMediaBuilder builder = builders.get(type);
        if (builder == null) {
            return null;
        }
        return builder.getInputMedia(fileId);
    }
}
