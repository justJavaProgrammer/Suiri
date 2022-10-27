package com.odeyalo.bot.suiri.support.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Container to store LanguageResponseMessageResolverStrategy.
 * Provides easy access to LanguageResponseMessageResolverStrategy by language code
 * @see com.odeyalo.bot.suiri.support.lang.LanguageResponseMessageResolverStrategy
 */
@Component
public class LanguageResponseMessageResolverContainer implements LanguageResponseMessageResolverRegistry {
    private final Map<String, LanguageResponseMessageResolverStrategy> resolvers;
    private final Logger logger = LoggerFactory.getLogger(LanguageResponseMessageResolverContainer.class);

    public LanguageResponseMessageResolverContainer(Map<String, LanguageResponseMessageResolverStrategy> resolvers) {
        this.resolvers = resolvers;
        this.logger.info("Initialized container with: {} elements. Elements: {}", resolvers.size(), new ArrayList<>(resolvers.values()));
    }

    @Autowired
    public LanguageResponseMessageResolverContainer(List<LanguageResponseMessageResolverStrategy> resolvers) {
        this(resolvers.stream().collect(Collectors.toMap(LanguageResponseMessageResolverStrategy::languageCode, Function.identity())));
    }


    @Override
    public void registry(String languageCode, LanguageResponseMessageResolverStrategy resolver) {
        this.resolvers.put(languageCode, resolver);
        this.logger.info("Registered resolver: {} with language code: {}", resolver, languageCode);
    }

    @Override
    public LanguageResponseMessageResolverStrategy getResolver(String languageCode) {
        return resolvers.get(languageCode);
    }

    @Override
    public LanguageResponseMessageResolverStrategy getOrDefault(String languageCode, String defaultLanguage) {
        LanguageResponseMessageResolverStrategy defaultValue = resolvers.get(defaultLanguage);
        return getOrDefault(languageCode, defaultValue);
    }

    @Override
    public LanguageResponseMessageResolverStrategy getOrDefault(String languageCode, LanguageResponseMessageResolverStrategy resolver) {
        return resolvers.getOrDefault(languageCode, resolver);
    }

    @Override
    public boolean containsLanguageCode(String langCode) {
        return resolvers.containsKey(langCode);
    }

    @Override
    public void delete(String langCode) {
        this.resolvers.remove(langCode);
    }
}
