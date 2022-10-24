package com.odeyalo.bot.suiri.repository;

import com.odeyalo.bot.suiri.entity.Dictionary;
import com.odeyalo.bot.suiri.entity.DictionaryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryItemRepository extends JpaRepository<DictionaryItem, Long> {

    DictionaryItem findDictionaryItemByOriginalTextAndDictionary(String originalText, Dictionary dictionary);

    DictionaryItem findDictionaryItemByTranslatedTextAndDictionary(String translatedText, Dictionary dictionary);

    DictionaryItem findDictionaryItemByPictureAndDictionary(String picture, Dictionary dictionary);


    void deleteByOriginalTextAndDictionary(String origText, Dictionary dictionary);

    void deleteByTranslatedTextAndDictionary(String translatedText, Dictionary dictionary);

    void deleteDictionaryItemByDictionaryAndOriginalText(Dictionary dictionary, String origText);
}
