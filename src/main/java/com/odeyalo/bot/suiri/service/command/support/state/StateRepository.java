package com.odeyalo.bot.suiri.service.command.support.state;

public interface StateRepository<T> {
    void saveState(String id, T state);

    T findStateById(String id);

    void deleteById(String id);
}
