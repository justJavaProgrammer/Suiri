package com.odeyalo.bot.suiri.service.command.support.translate;

import com.odeyalo.bot.suiri.service.command.support.translate.dto.TranslateWordYandexApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Set;

/**
 * When exception is occurred this class will attempt to retry translate given word using other Yandex API service
 */
@Component
public class HttpClientYandexAPISuggestWordTranslateRequestExceptionHandler implements SuggestWordTranslateRequestExceptionHandler {
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(HttpClientYandexAPISuggestWordTranslateRequestExceptionHandler.class);

    private final YandexSessionUpdater sessionUpdater;
    private final String url;
    private final String ID_KEY = "id";
    private String idValue;
    private final String SRV_KEY = "srv";
    private final String SRV_VALUE = "tr-text";
    private final String LANG_KEY = "lang";
    private final String REASON_KEY = "reason";
    private final String REASON_AUTO_VALUE = "auto";
    private final String TEXT_KEY = "text";
    private final String FORMAT_KEY = "format";
    private final String FORMAT_TEXT_VALUE = "text";
    private final String SESSION_SUFFIX = "-0-0";

    @Autowired
    public HttpClientYandexAPISuggestWordTranslateRequestExceptionHandler(RestTemplate restTemplate,
                                                                          YandexSessionUpdater sessionUpdater,
                                                                          @Value("${app.translate.detect.language.yandex.http.url.translate}") String url) {
        this.restTemplate = restTemplate;
        this.sessionUpdater = sessionUpdater;
        this.url = url;
    }

    @Override
    public Set<String> suggestWords(String word, String fromLanguage, String toLanguage) {
        this.idValue = this.sessionUpdater.updateIfExpired();
        String requestUrl = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam(ID_KEY, idValue + SESSION_SUFFIX)
                .queryParam(SRV_KEY, SRV_VALUE)
                .queryParam(LANG_KEY, fromLanguage + "-" + toLanguage)
                .queryParam(REASON_KEY, REASON_AUTO_VALUE)
                .queryParam(FORMAT_KEY, FORMAT_TEXT_VALUE)
                .queryParam(TEXT_KEY, word).build().toUriString();
        this.logger.info("Retry to suggest word: {}", requestUrl);
        ResponseEntity<TranslateWordYandexApiResponse> entity = this.restTemplate.postForEntity(requestUrl, "", TranslateWordYandexApiResponse.class);
        if (!entity.getStatusCode().is2xxSuccessful() || entity.getBody() == null) {
            return Collections.emptySet();
        }
        return entity.getBody().getText();
    }

    @Override
    public void handleException(HttpClientErrorException exception) {
        this.logger.error("Exception during request sending", exception);
    }
}
