package com.odeyalo.bot.suiri.service.command.steps.settings.test;


public enum ChangePreferredTestType {
    START,
    CHANGE_PREFERRED_TESTING_TYPE;

    private final static ChangePreferredTestType[] order = values();

    public static ChangePreferredTestType last() {
        return order[order.length - 1];
    }


    public ChangePreferredTestType next() {
        return order[(this.ordinal() + 1) % order.length];
    }
}
