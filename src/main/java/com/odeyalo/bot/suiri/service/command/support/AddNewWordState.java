package com.odeyalo.bot.suiri.service.command.support;

public enum AddNewWordState {
    START(false),
    ORIGINAL_WORD(false),
    TRANSLATED_WORD(false),
    PICTURE(true),
    FINISH(false);

    private final boolean isOptional;

    AddNewWordState(boolean optional) {
        this.isOptional = optional;
    }

    public boolean isOptional() {
        return isOptional;
    }
}
