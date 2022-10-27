package com.odeyalo.bot.suiri.service.command.steps;

import com.odeyalo.bot.suiri.service.command.support.state.AddNewWordState;
import com.odeyalo.bot.suiri.service.command.support.state.AddNewWordStateRepository;
import com.odeyalo.bot.suiri.support.lang.ResponseMessageResolverDecorator;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAddNewWordMessageBuildingStep implements AddNewWordMessageBuildingStep {
    protected final AddNewWordStateRepository stateRepository;
    protected final ResponseMessageResolverDecorator responseMessageResolverDecorator;

    @Autowired
    public AbstractAddNewWordMessageBuildingStep(AddNewWordStateRepository stateRepository, ResponseMessageResolverDecorator responseMessageResolverDecorator) {
        this.stateRepository = stateRepository;
        this.responseMessageResolverDecorator = responseMessageResolverDecorator;
    }

    public AddNewWordState getCurrentState(String id) {
        return stateRepository.findStateById(id);
    }
}
