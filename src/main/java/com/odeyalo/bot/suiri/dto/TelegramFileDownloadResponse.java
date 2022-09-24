package com.odeyalo.bot.suiri.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.File;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TelegramFileDownloadResponse {
    private boolean ok;
    @JsonProperty("result")
    private File body;
}
