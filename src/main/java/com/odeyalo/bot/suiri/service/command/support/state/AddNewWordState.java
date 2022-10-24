package com.odeyalo.bot.suiri.service.command.support.state;

public enum AddNewWordState {
    START(false),
    ORIGINAL_WORD(false),
    TRANSLATED_WORD(false),
    PICTURE(true),
    FINISH(false);

    private static final AddNewWordState[] order = values();

    private final boolean isOptional;

    AddNewWordState(boolean optional) {
        this.isOptional = optional;
    }

    /**
     * Returns the next state of the current.
     * Example: if current state is START then next() returns ORIGINAL_WORD state
     * @return - next state
     */
    public AddNewWordState next() {
        return order[(this.ordinal()+1) % order.length];
    }

    public boolean isOptional() {
        return isOptional;
    }
}
