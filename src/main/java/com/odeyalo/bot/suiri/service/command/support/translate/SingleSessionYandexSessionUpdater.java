package com.odeyalo.bot.suiri.service.command.support.translate;

import com.odeyalo.bot.suiri.exception.SessionResolvingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default implementation of YandexSessionUpdater that use http request to get a new session id
 */
@Service
public class SingleSessionYandexSessionUpdater implements YandexSessionUpdater {
    private final String url = "http://translate.yandex.com/";
    private final String SESSION_ID_PATTERN_START = "Ya.reqid = \'";
    private final int SESSION_ID_PATTERN_START_LENGTH = SESSION_ID_PATTERN_START.length();
    private final String SESSION_ID_PATTERN_END = "\';";
    private final int SESSION_ID_PATTERN_END_LENGTH = SESSION_ID_PATTERN_END.length();
    private final Integer SESSION_MAX_AGE_SECONDS = 10800;
    /*
     * Map with cached session to decrease API calls to Yandex API and to avoid captcha
     * Map always contains only one element
     * Key - session id
     * Value - Time when session is expire
     */
    private final Map<String, LocalDateTime> cachedSessions = new ConcurrentHashMap<>();


    @Override
    public String getSessionId() {
        try {
            Document document = Jsoup.connect(url).get();
            Elements scripts = document.select("script");
            for (Element script : scripts) {
                String html = script.html();
                int index = html.indexOf(SESSION_ID_PATTERN_START);
                if (index != -1) {
                    int endIndex = html.indexOf(SESSION_ID_PATTERN_END, index);
                    String sessionId = html.substring(index + SESSION_ID_PATTERN_START_LENGTH, endIndex);
                    this.cachedSessions.clear();
                    this.cachedSessions.put(sessionId, LocalDateTime.now().plusSeconds(SESSION_MAX_AGE_SECONDS));
                    return sessionId;
                }
            }
        } catch (IOException ex) {
            throw new SessionResolvingException("Failed to resolve session", ex);
        }
        throw new SessionResolvingException("Failed to resolve session since Yandex API returns nothing");
    }

    @Override
    public String updateIfExpired() {
        Set<String> strings = this.cachedSessions.keySet();
        if (!strings.isEmpty()) {
            String sessionId = new ArrayList<>(strings).get(0);
            LocalDateTime time = this.cachedSessions.get(sessionId);
            if (time.isAfter(LocalDateTime.now())) {
                return sessionId;
            }
        }
        return getSessionId();
    }
}
