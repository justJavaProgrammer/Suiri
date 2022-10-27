package com.odeyalo.bot.suiri.service.command.support.state.settings;

public enum ChangeLanguageState {
    START,
    CHANGE_LANGUAGE,
    FINISH;

    private static final ChangeLanguageState[] order = values();

    public ChangeLanguageState next() {
        return order[(this.ordinal() + 1) % order.length];
    }

}
