package com.odeyalo.bot.suiri.service.command.steps;

import com.odeyalo.bot.suiri.service.command.support.AddNewWordState;
import com.odeyalo.bot.suiri.service.command.support.AddNewWordStateRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAddNewWordBuildingStep implements AddNewWordBuildingStep {
    protected final AddNewWordStateRepository stateRepository;

    @Autowired
    public AbstractAddNewWordBuildingStep(AddNewWordStateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public AddNewWordState getCurrentState(String id) {
        return stateRepository.findStateById(id);
    }
}
