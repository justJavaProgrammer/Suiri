package com.odeyalo.bot.suiri.repository;

import com.odeyalo.bot.suiri.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByTelegramId(String telegramId);
}
