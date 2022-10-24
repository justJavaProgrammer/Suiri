package com.odeyalo.bot.suiri.service.command.support.state.delete;

public enum DeleteWordState {
    START,
    DELETE_WORD,
    FINISH;

    private static final DeleteWordState[] order = DeleteWordState.values();


    public DeleteWordState next() {
        return order[(this.ordinal() + 1) % order.length];
    }
}
