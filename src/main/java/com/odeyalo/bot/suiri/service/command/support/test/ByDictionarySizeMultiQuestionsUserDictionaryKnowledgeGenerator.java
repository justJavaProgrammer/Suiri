package com.odeyalo.bot.suiri.service.command.support.test;

import com.odeyalo.bot.suiri.entity.DictionaryItem;
import com.odeyalo.bot.suiri.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Generates list of questions by user's dictionary size
 */
@Service
public class ByDictionarySizeMultiQuestionsUserDictionaryKnowledgeGenerator implements MultiQuestionsUserDictionaryKnowledgeGenerator {
    private final UserDictionaryKnowledgeTestGenerator generator;

    @Autowired
    public ByDictionarySizeMultiQuestionsUserDictionaryKnowledgeGenerator(UserDictionaryKnowledgeTestGenerator generator) {
        this.generator = generator;
    }

    @Override
    public List<UserDictionaryKnowledgeTest> generateTests(User user, Integer limit) {
        List<DictionaryItem> items = user.getUserDictionary().getItems();
        List<UserDictionaryKnowledgeTest> result;
        if (items.size() >= limit) {
            result = generate(user, limit);
        } else {
            result = generate(user, items.size());
        }
        return result;
    }

    private List<UserDictionaryKnowledgeTest> generate(User user, int size) {
        Set<UserDictionaryKnowledgeTest> tests = new HashSet<>();
        while (tests.size() != size) {
            UserDictionaryKnowledgeTest test = this.generator.generateTest(user);
            tests.add(test);
        }
        ArrayList<UserDictionaryKnowledgeTest> list = new ArrayList<>(tests);
        Collections.shuffle(list);
        return list;
    }
}
