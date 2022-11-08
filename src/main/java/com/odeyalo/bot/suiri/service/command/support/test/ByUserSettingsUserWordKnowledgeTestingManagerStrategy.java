package com.odeyalo.bot.suiri.service.command.support.test;

import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.entity.UserSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Pick implementation based on user preferred setting
 */
@Service
public class ByUserSettingsUserWordKnowledgeTestingManagerStrategy implements UserWordKnowledgeTestingManagerStrategy {
    private final Map<UserSettings.PreferredKnowledgeTestType, UserWordKnowledgeTestingManager> managers;
    private final Logger logger = LoggerFactory.getLogger(ByUserSettingsUserWordKnowledgeTestingManagerStrategy.class);

    /**
     * Initialize this strategy with list of UserWordKnowledgeTestingManager
     * @param managers - list of UserWordKnowledgeTestingManager
     */
    @Autowired
    public ByUserSettingsUserWordKnowledgeTestingManagerStrategy(List<UserWordKnowledgeTestingManager> managers) {
        this.managers = managers.stream().collect(Collectors.toMap(UserWordKnowledgeTestingManager::getType, Function.identity()));
        this.logger.info("Initialized strategy with: {} elements. {}", managers.size(), managers);
    }

    @Override
    public PartialBotApiMethod<?> getUserKnowledgeTest(Update update, User user) {
        UserSettings.PreferredKnowledgeTestType testType = user.getUserSettings().getTestType();
        // Delegate handling to UserWordKnowledgeTestingManager by testType
        UserWordKnowledgeTestingManager manager = managers.get(testType);
        this.logger.info("Delegate to: {} since testType for user: {} is {}", manager, user, testType);
        return manager.getUserKnowledgeTest(update, user);
    }
}
