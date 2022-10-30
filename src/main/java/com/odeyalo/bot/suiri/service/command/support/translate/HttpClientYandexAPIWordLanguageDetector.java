package com.odeyalo.bot.suiri.service.command.support.translate;

import com.odeyalo.bot.suiri.service.command.support.translate.dto.DetectLanguageYandexApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Detect language code using Yandex API using plain http client
 */
@Service
public class HttpClientYandexAPIWordLanguageDetector implements WordLanguageDetector {
    private final RestTemplate restTemplate;
    private final YandexSessionUpdater sessionUpdater;
    private final String url;

    private final String SESSION_ID_KEY = "sid";
    private String sessionIdValue;
    private final String SRV_KEY = "srv";
    private final String SRV_VALUE = "tr-text";
    private String TEXT_KEY = "text";
    private String OPTIONS_KEY = "options";
    private final Logger logger = LoggerFactory.getLogger(HttpClientYandexAPIWordLanguageDetector.class);

    @Autowired
    public HttpClientYandexAPIWordLanguageDetector(RestTemplate restTemplate,
                                                   YandexSessionUpdater sessionUpdater,
                                                   @Value("${app.translate.detect.language.yandex.http.url.detect}") String url) {
        this.restTemplate = restTemplate;
        this.sessionUpdater = sessionUpdater;
        this.url = url;
    }

    @Override
    public String detectLanguageCode(String word) {
        this.sessionIdValue = this.sessionUpdater.updateIfExpired();
        String requestUrl = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam(SESSION_ID_KEY, sessionIdValue)
                .queryParam(SRV_KEY, SRV_VALUE)
                .queryParam(TEXT_KEY, word)
                .queryParam(OPTIONS_KEY, 1).build().toUriString();
        this.logger.info("Sending request to: {}", requestUrl);
        DetectLanguageYandexApiResponse response = this.restTemplate.getForObject(requestUrl, DetectLanguageYandexApiResponse.class);
        if (response == null) {
            this.logger.info("Failed to detect language for: {}", word);
            return "en";
        }
        String lang = response.getLang();
        this.logger.info("Resolved language: {} for word: {}", lang, word);
        return lang;
    }
}
