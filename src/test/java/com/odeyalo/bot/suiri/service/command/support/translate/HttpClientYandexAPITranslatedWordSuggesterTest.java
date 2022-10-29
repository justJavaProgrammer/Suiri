package com.odeyalo.bot.suiri.service.command.support.translate;

import com.odeyalo.bot.suiri.AbstractIntegrationTest;
import com.odeyalo.bot.suiri.support.lang.Languages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class HttpClientYandexAPITranslatedWordSuggesterTest extends AbstractIntegrationTest {
    @Autowired
    private HttpClientYandexAPITranslatedWordSuggester suggester;

    @Test
    void suggestWords() {
        Set<String> set = this.suggester.suggestWords("Hello", Languages.UKRAINIAN, 3);

        assertEquals(set.size(), 3);

    }
}
