package com.odeyalo.bot.suiri.support.lang;


import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * Abstract class that provides utility methods to get property from ".properties" file
 */
public abstract class AbstractPropertySourceLanguageResponseMessageResolverStrategy implements LanguageResponseMessageResolverStrategy {
    private final PropertyResolver propertyResolver;

    public AbstractPropertySourceLanguageResponseMessageResolverStrategy(String pathToProperties) throws IOException {
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addFirst(new ResourcePropertySource(pathToProperties));
        this.propertyResolver = new PropertySourcesPropertyResolver(propertySources);
    }

    public AbstractPropertySourceLanguageResponseMessageResolverStrategy(ResourcePropertySource propertySource) throws IOException {
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addFirst(propertySource);
        this.propertyResolver = new PropertySourcesPropertyResolver(propertySources);
    }

    protected String doGetProperty(String key) {
        return propertyResolver.getProperty(key);
    }
}
