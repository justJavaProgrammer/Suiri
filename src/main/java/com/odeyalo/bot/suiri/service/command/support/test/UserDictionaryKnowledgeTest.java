package com.odeyalo.bot.suiri.service.command.support.test;

import com.odeyalo.bot.suiri.entity.DictionaryItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.SortedSet;

/**
 *  Data class that contains options, correct answer, picture, original text and explanation to test
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDictionaryKnowledgeTest {
    private String picture;
    private DictionaryItem.PictureType pictureType;
    private String originalText;
    private String correctAnswer;
    private SortedSet<String> options;
    private String explanation;
}
