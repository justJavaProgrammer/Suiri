package com.odeyalo.bot.suiri.service.command.support.state.settings.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreferredTestTypeMessage {
    private String userId;
    private String to;
}
