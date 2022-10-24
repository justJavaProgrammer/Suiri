package com.odeyalo.bot.suiri.service.dictionary.item.delete;

import com.odeyalo.bot.suiri.entity.DictionaryItem;
import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.repository.DictionaryItemRepository;
import com.odeyalo.bot.suiri.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictionaryItemServiceImpl implements DictionaryItemService {
    private final DictionaryItemRepository dictionaryItemRepository;

    @Autowired
    public DictionaryItemServiceImpl(DictionaryItemRepository dictionaryItemRepository) {
        this.dictionaryItemRepository = dictionaryItemRepository;
    }

    @Override
    public void saveDictionaryItem(DictionaryItem dictionaryItem) {
        this.dictionaryItemRepository.save(dictionaryItem);
    }

    @Override
    public void delete(DictionaryItem dictionaryItem) {
        this.dictionaryItemRepository.delete(dictionaryItem);
    }
}
