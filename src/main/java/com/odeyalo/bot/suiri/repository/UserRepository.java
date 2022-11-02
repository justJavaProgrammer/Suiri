package com.odeyalo.bot.suiri.repository;

import com.odeyalo.bot.suiri.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by telegram id
     * @param telegramId - user's telegram id
     * @return - user by telegram id
     */
    User findUserByTelegramId(String telegramId);

    /**
     * Search users by enableNotification param
     * @param enableNotification - user notification's settings
     * @return - list of users searched by this param
     */
    List<User> findAllByUserSettingsEnableNotification(boolean enableNotification);
}
