package com.odeyalo.bot.suiri.service.dictionary.item.delete;

import com.odeyalo.bot.suiri.entity.DictionaryItem;
import com.odeyalo.bot.suiri.entity.User;

public interface DictionaryItemService {

    void saveDictionaryItem(DictionaryItem dictionaryItem);


    void delete(DictionaryItem dictionaryItem);


}
