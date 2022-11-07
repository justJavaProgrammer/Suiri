package com.odeyalo.bot.suiri.service.command.support.test.store;

import com.odeyalo.bot.suiri.service.command.support.test.Questions;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Log4j2
public class UserKnowledgeTestingStoreImpl implements UserKnowledgeTestingStore {
    private final Map<String, Questions> questionsMap;

    public UserKnowledgeTestingStoreImpl() {
        questionsMap = new ConcurrentHashMap<>();
    }

    @Override
    public void save(String id, Questions questions) {
        this.questionsMap.put(id, questions);
        log.info("Saved: {} with id: {}", questions, id);
    }

    @Override
    public Questions getById(String id) {
        return questionsMap.get(id);
    }

    @Override
    public void remove(String id) {
        this.questionsMap.remove(id);
    }
}
