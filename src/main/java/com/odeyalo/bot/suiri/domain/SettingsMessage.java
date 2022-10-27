package com.odeyalo.bot.suiri.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettingsMessage {
    private String userId;
    private String changeTo;
}
