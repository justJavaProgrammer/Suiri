package com.odeyalo.bot.suiri.service.command.support.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class ImMemoryStateRepository<T> implements StateRepository<T> {
    private final ConcurrentMap<String, T> states;

    @Autowired
    public ImMemoryStateRepository() {
        this.states = new ConcurrentHashMap<>();
    }

    public ImMemoryStateRepository(ConcurrentMap<String, T> states) {
        this.states = states;
    }

    @Override
    public void saveState(String id, T state) {
        states.put(id, state);
    }

    @Override
    public T findStateById(String id) {
        return states.get(id);
    }

    @Override
    public void deleteById(String id) {
        this.states.remove(id);
    }
}
