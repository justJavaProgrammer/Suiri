package com.odeyalo.bot.suiri.service.command.support.translate;

import com.odeyalo.bot.suiri.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class HttpClientYandexAPIWordLanguageDetectorTest extends AbstractIntegrationTest {

    @Autowired
    private HttpClientYandexAPIWordLanguageDetector detector;

    @Test
    void detectLanguageCode() {
        String hello = this.detector.detectLanguageCode("Hello");
        assertEquals("en", hello);
    }
}
