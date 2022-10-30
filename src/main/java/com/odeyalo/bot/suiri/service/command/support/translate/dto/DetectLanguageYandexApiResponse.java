package com.odeyalo.bot.suiri.service.command.support.translate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetectLanguageYandexApiResponse {
    private Integer code;
    private String lang;
}
