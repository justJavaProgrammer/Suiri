package com.odeyalo.bot.suiri.service.command.support.state.settings;

import com.odeyalo.bot.suiri.domain.SettingsMessage;
import com.odeyalo.bot.suiri.service.command.steps.settings.lang.ChangeLanguageMessage;
import com.odeyalo.bot.suiri.service.command.steps.settings.lang.ChangeUserLanguageSettingsStep;
import com.odeyalo.bot.suiri.service.command.support.state.ImMemoryStateRepository;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Change user's preferred language using states
 *
 */
@Component
public class LanguageUserSettingsChanger implements UserSettingsChanger {
    /**
     * Repository that stores current state for user
     */
    private final ImMemoryStateRepository<ChangeLanguageState> stateRepository;
    /**
     * Contains steps to change user's preferred language
     */
    private final Map<ChangeLanguageState, ChangeUserLanguageSettingsStep> steps;

    @Autowired
    public LanguageUserSettingsChanger(ImMemoryStateRepository<ChangeLanguageState> stateRepository, List<ChangeUserLanguageSettingsStep> steps) {
        this.stateRepository = stateRepository;
        this.steps = steps.stream().collect(Collectors.toMap(ChangeUserLanguageSettingsStep::getState, Function.identity()));
    }

    @Override
    public BotApiMethod<?> change(Update update, SettingsMessage data) {
        String id = String.valueOf(TelegramUtils.getTelegramUser(update).getId());

        ChangeLanguageState state = this.stateRepository.findStateById(id);
        if (state == null || getName().equals(TelegramUtils.getText(update))) {
            state = ChangeLanguageState.START;
        }
        BotApiMethod<?> method = steps.get(state).processStep(update, new ChangeLanguageMessage(id, data.getChangeTo()));
        stateRepository.saveState(id, state.next());

        if (state == ChangeLanguageState.FINISH) {
            this.stateRepository.deleteById(id);
        }
        return method;
    }

    @Override
    public String getName() {
        return "Language";
    }
}
