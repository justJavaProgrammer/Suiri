package com.odeyalo.bot.suiri.service.command.support.translate.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Simple DTO class that wraps Yandex api response to List of TranslateWordYandexApiResponse class
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ListTranslateWordYandexApiResponse {

    private List<Result> result;


    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Pos {
        public String code;
        public String text;
        public String tooltip;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        public String text;
        public Pos pos;
        public Translation translation;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Tab {
        public String text;
        public Pos pos;
        public Translation translation;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Translation {
        public String text;
        public Pos pos;
        public boolean hide;
        public boolean idiom;
        @JsonProperty("StackOverflow")
        public boolean stackOverflow;
        public boolean other;
    }
}
