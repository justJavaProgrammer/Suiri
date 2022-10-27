package com.odeyalo.bot.suiri.support.lang;

import java.util.HashMap;
import java.util.Map;

public class LanguageToLanguageCodeConvertor {
    /**
     * Key - language
     * Value - language code
     */
    private static final Map<String, String> codes = new HashMap<>();

    static {
        codes.put("English", Languages.ENGLISH);
        codes.put("Ukrainian", Languages.UKRAINIAN);
    }

    public static String convert(String lang) {
        return codes.get(lang);
    }
}
