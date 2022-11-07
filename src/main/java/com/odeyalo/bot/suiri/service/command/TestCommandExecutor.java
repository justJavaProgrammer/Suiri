package com.odeyalo.bot.suiri.service.command;

import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.repository.UserRepository;
import com.odeyalo.bot.suiri.service.command.support.test.UserWordKnowledgeTestingManager;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * CommandExecutor implementation that executes '/test' command. This class is test user knowledge using different strategies
 * Current supported strategies to test user knowledge:
 *  Using quiz poll
 *  Using updatable messages
 */
@Service
public class TestCommandExecutor implements CommandExecutor {
    private static final String COMMAND_NAME = "/test";
    private final UserRepository userRepository;
    private final UserWordKnowledgeTestingManager userWordKnowledgeTestingManager;

    private final Logger logger = LoggerFactory.getLogger(TestCommandExecutor.class);

    @Autowired
    public TestCommandExecutor(UserRepository userRepository, @Qualifier("updatableMessageUserWordKnowledgeTestingManager") UserWordKnowledgeTestingManager userWordKnowledgeTestingManager) {
        this.userRepository = userRepository;
        this.userWordKnowledgeTestingManager = userWordKnowledgeTestingManager;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        Long telegramId = TelegramUtils.getTelegramUser(update).getId();
        User user = this.userRepository.findUserByTelegramId(String.valueOf(telegramId));

        return (BotApiMethod<?>) userWordKnowledgeTestingManager.getUserKnowledgeTest(update, user);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public boolean isMultiStepCommand() {
        return false;
    }
}
