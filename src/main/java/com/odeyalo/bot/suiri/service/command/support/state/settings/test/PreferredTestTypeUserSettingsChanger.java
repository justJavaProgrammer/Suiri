package com.odeyalo.bot.suiri.service.command.support.state.settings.test;

import com.odeyalo.bot.suiri.domain.SettingsMessage;
import com.odeyalo.bot.suiri.service.command.steps.settings.test.ChangePreferredTestType;
import com.odeyalo.bot.suiri.service.command.steps.settings.test.UserPreferredTestTypeSettingsChangeStep;
import com.odeyalo.bot.suiri.service.command.support.MultiLanguageCommandTranslatorDelegate;
import com.odeyalo.bot.suiri.service.command.support.state.settings.UserSettingsChanger;
import com.odeyalo.bot.suiri.support.TelegramUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Change user's preferred test knowledge test type
 */
@Service
public class PreferredTestTypeUserSettingsChanger implements UserSettingsChanger {
    private final Map<ChangePreferredTestType, UserPreferredTestTypeSettingsChangeStep> stepsMap;
    private final PreferredTestTypeChangeImMemoryStateRepository stateRepository;

    @Autowired
    public PreferredTestTypeUserSettingsChanger(List<UserPreferredTestTypeSettingsChangeStep> stepsList, PreferredTestTypeChangeImMemoryStateRepository stateRepository) {
        this.stateRepository = stateRepository;
        this.stepsMap = stepsList.stream().collect(Collectors.toMap(UserPreferredTestTypeSettingsChangeStep::getType, Function.identity()));
    }

    @Override
    public BotApiMethod<?> change(Update update, SettingsMessage data) {
        String id = String.valueOf(TelegramUtils.getTelegramUser(update).getId());

        ChangePreferredTestType state = this.stateRepository.findStateById(id);
        if (state == null || getName().equals(TelegramUtils.getText(update))) {
            state = ChangePreferredTestType.START;
        }
        BotApiMethod<?> method = stepsMap.get(state).processStep(update, new PreferredTestTypeMessage(id, data.getChangeTo()));
        stateRepository.saveState(id, state.next());

        if (state == ChangePreferredTestType.last()) {
            this.stateRepository.deleteById(id);
        }
        return method;
    }

    @Override
    public String getName() {
        return "Preferred test type";
    }
}
