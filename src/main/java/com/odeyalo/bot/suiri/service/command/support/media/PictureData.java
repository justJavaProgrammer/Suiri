package com.odeyalo.bot.suiri.service.command.support.media;

import com.odeyalo.bot.suiri.entity.DictionaryItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contains data about picture
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PictureData {
    private String pictureId;
    private DictionaryItem.PictureType pictureType;
}
