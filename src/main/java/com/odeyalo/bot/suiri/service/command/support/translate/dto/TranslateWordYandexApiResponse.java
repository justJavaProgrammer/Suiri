package com.odeyalo.bot.suiri.service.command.support.translate.dto;


import lombok.Data;

import java.util.Set;

@Data
public class TranslateWordYandexApiResponse {
    private String code;
    private Set<String> text;
}
