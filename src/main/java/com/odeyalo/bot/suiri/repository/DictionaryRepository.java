package com.odeyalo.bot.suiri.repository;

import com.odeyalo.bot.suiri.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

    Dictionary findDictionaryByUserId(Long userId);
}
