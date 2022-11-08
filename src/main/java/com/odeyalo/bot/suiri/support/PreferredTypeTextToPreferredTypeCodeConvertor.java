package com.odeyalo.bot.suiri.support;

import java.util.HashMap;
import java.util.Map;

public class PreferredTypeTextToPreferredTypeCodeConvertor {
    private static final Map<String, String> codes = new HashMap<>();


    static {
        codes.put("Quiz poll", "QUIZ_POLL");
        codes.put("Updatable message", "UPDATABLE_MESSAGE");
        codes.put("Опитування", "QUIZ_POLL");
        codes.put("Месседжа", "UPDATABLE_MESSAGE");
    }

    public static String getCode(String key) {
        return codes.get(key);
    }
}
