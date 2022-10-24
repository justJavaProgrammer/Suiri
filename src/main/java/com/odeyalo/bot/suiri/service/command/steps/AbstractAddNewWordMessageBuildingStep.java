package com.odeyalo.bot.suiri.service.command.steps;

import com.odeyalo.bot.suiri.service.command.support.state.AddNewWordState;
import com.odeyalo.bot.suiri.service.command.support.state.AddNewWordStateRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAddNewWordMessageBuildingStep implements AddNewWordMessageBuildingStep {
    protected final AddNewWordStateRepository stateRepository;

    @Autowired
    public AbstractAddNewWordMessageBuildingStep(AddNewWordStateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public AddNewWordState getCurrentState(String id) {
        return stateRepository.findStateById(id);
    }
}
