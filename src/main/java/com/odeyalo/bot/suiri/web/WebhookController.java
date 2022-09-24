package com.odeyalo.bot.suiri.web;

import com.odeyalo.bot.suiri.domain.BotApiMethodEntrypoint;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequestMapping("/hook/")
@RestController
public class WebhookController {
    private final BotApiMethodEntrypoint entrypoint;

    public WebhookController(BotApiMethodEntrypoint entrypoint) {
        this.entrypoint = entrypoint;
    }

    @PostMapping("/telegram")
    public BotApiMethod<?> telegramWebhook(@RequestBody Update update) {
        return this.entrypoint.onWebhookUpdateReceived(update);
    }
}
