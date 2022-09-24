package com.odeyalo.bot.suiri.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddNewWordMessage {
    private String userId;
    private String originalWord;
    private List<String> translatedWords;
}
