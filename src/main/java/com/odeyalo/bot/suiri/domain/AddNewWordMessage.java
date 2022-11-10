package com.odeyalo.bot.suiri.domain;

import com.odeyalo.bot.suiri.entity.DictionaryItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddNewWordMessage {
    private String userId;
    private String originalWord;
    private List<String> translatedWords;
    private String picture;
    private DictionaryItem.PictureType pictureType;
}
