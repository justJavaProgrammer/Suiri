package com.odeyalo.bot.suiri.service.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto class that contains chat id to send message and text
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationMessage {
    private String chatId;
    private String message;
}
