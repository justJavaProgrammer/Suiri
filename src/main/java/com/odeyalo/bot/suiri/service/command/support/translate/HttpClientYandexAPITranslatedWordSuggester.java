package com.odeyalo.bot.suiri.service.command.support.translate;

import com.odeyalo.bot.suiri.service.command.support.translate.dto.ListTranslateWordYandexApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * AbstractYandexAPITranslatedWordSuggester implementation that use no api keys to send HTTP request and use plain HttpClient
 */
@Service
public class HttpClientYandexAPITranslatedWordSuggester implements AbstractYandexAPITranslatedWordSuggester {
    private final RestTemplate restTemplate;
    private final String rootUrl;
    private final WordLanguageDetector wordLanguageDetector;
    private final YandexSessionUpdater sessionUpdater;
    private final SuggestWordTranslateRequestExceptionHandler exceptionHandler;
    private final String SESSION_ID_KEY = "sid";
    private String sessionIdValue;
    private final String SRV_KEY = "srv";
    private final String SRV_VALUE = "tr-text";
    private final String UI_KEY = "ui";
    private final String TEXT_KEY = "text";
    private final String OPTIONS_KEY = "options";
    private final String LANG_KEY = "lang";
    private final String SRC_KEY = "src";
    private final String CHUNKS_KEY = "chunks";
    private final String MAX_LEN_KEY = "maxlen";
    private final String DEFAULT_LANGUAGE_CODE = "en";
    private final Logger logger = LoggerFactory.getLogger(HttpClientYandexAPITranslatedWordSuggester.class);


    @Autowired
    public HttpClientYandexAPITranslatedWordSuggester(RestTemplate restTemplate,
                                                      @Value("${app.translate.yandex.http.dictionary.url}") String rootUrl,
                                                      WordLanguageDetector wordLanguageDetector,
                                                      YandexSessionUpdater sessionUpdater,
                                                      SuggestWordTranslateRequestExceptionHandler exceptionHandler) {
        this.restTemplate = restTemplate;
        this.rootUrl = rootUrl;
        this.wordLanguageDetector = wordLanguageDetector;
        this.sessionUpdater = sessionUpdater;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public Set<String> suggestWords(String originalWord, String requiredLanguage, Integer size) {
        String languageCode = this.wordLanguageDetector.detectLanguageCode(originalWord);
        this.sessionIdValue = this.sessionUpdater.updateIfExpired();
        String requestUrl = UriComponentsBuilder.fromHttpUrl(rootUrl)
                .queryParam(SESSION_ID_KEY, sessionIdValue)
                .queryParam(UI_KEY, languageCode)
                .queryParam(SRV_KEY, SRV_VALUE)
                .queryParam(SRC_KEY, originalWord)
                .queryParam(TEXT_KEY, originalWord)
                .queryParam(OPTIONS_KEY, 226)
                .queryParam(LANG_KEY, getLangCode(requiredLanguage, languageCode))
                .queryParam(CHUNKS_KEY, 1)
                .queryParam(MAX_LEN_KEY, 20).build().toUriString();
        this.logger.info(requestUrl);
        try {
            return getSuggestedWords(size, requestUrl);
        } catch (HttpClientErrorException.BadRequest exception) {
            this.logger.error("Bad request occurred while request sending", exception);
            return this.exceptionHandler.suggestWords(originalWord, languageCode, requiredLanguage);
        }
    }

    private String getLangCode(String requiredLanguage, String languageCode) {
        return languageCode.equals(requiredLanguage) ? languageCode + "-" + DEFAULT_LANGUAGE_CODE : languageCode + "-" + requiredLanguage;
    }

    private Set<String> getSuggestedWords(Integer size, String requestUrl) {
        ResponseEntity<ListTranslateWordYandexApiResponse> responseEntity = this.restTemplate.getForEntity(requestUrl, ListTranslateWordYandexApiResponse.class);
        /*
         * If request execution was not successful - return empty set
         */
        if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
            return Collections.emptySet();
        }

        ListTranslateWordYandexApiResponse body = responseEntity.getBody();
        List<ListTranslateWordYandexApiResponse.Result> responses = body.getResult().subList(0, size);

        return responses.stream().map(x -> x.translation.text).collect(Collectors.toSet());
    }
}
