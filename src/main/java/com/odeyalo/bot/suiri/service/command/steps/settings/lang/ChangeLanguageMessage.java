package com.odeyalo.bot.suiri.service.command.steps.settings.lang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeLanguageMessage {
    private String userId;
    private String language;
}
