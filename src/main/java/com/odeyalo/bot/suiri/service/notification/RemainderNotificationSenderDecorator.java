package com.odeyalo.bot.suiri.service.notification;

/**
 * Decorator that send remainder notifications to users
 */
public interface RemainderNotificationSenderDecorator {
    /**
     * Send remainder notification to users
     */
    void send();
}
