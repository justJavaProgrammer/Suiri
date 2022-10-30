package com.odeyalo.bot.suiri.service.command.support.translate;

import com.odeyalo.bot.suiri.exception.SessionResolvingException;

/**
 * Support interface to update session in Yandex API to perform http request
 */
public interface YandexSessionUpdater {

    /**
     * Returns a new session id from Yandex API. Never null
     * @return - session id from Yandex API
     */
    String getSessionId() throws SessionResolvingException;

    /**
     * Update session if given session id is already expired
     * @return - updated session id if expired, otherwise given session
     */
    String updateIfExpired();
}
