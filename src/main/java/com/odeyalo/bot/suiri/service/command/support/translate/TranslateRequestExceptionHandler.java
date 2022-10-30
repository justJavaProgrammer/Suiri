package com.odeyalo.bot.suiri.service.command.support.translate;


import org.springframework.web.client.HttpClientErrorException;

/**
 * Interface that handle request exception if request was not success.
 */
public interface TranslateRequestExceptionHandler {


    void handleException(HttpClientErrorException exception);

}
