package com.odeyalo.bot.suiri.service.command.support;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class InMemoryAddNewWordStateRepository implements AddNewWordStateRepository {
    private final ConcurrentMap<String, AddNewWordState> states;

    public InMemoryAddNewWordStateRepository() {
        this.states = new ConcurrentHashMap<>();
    }

    public InMemoryAddNewWordStateRepository(ConcurrentMap<String, AddNewWordState> states) {
        this.states = states;
    }

    @Override
    public void saveState(String id, AddNewWordState state) {
        states.put(id, state);
    }

    @Override
    public AddNewWordState findStateById(String id) {
        return states.get(id);
    }

    @Override
    public void deleteById(String id) {
        this.states.remove(id);
    }
}
