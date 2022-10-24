package com.odeyalo.bot.suiri.service.command.support;


import com.odeyalo.bot.suiri.entity.Dictionary;
import com.odeyalo.bot.suiri.entity.DictionaryItem;
import com.odeyalo.bot.suiri.repository.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class RandomUserWordGetterImpl implements RandomUserWordGetter {
    private final DictionaryRepository dictionaryRepository;

    @Autowired
    public RandomUserWordGetterImpl(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public DictionaryItem getRandomWord(String userId) {
        Dictionary dictionary = this.dictionaryRepository.findDictionaryByUserId(Long.valueOf(userId));
        List<DictionaryItem> dictionaryItems = dictionary.getItems();
        Random random = new Random();
        int item = random.nextInt(dictionaryItems.size());
        return dictionaryItems.get(item);
    }
}
