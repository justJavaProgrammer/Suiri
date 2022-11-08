package com.odeyalo.bot.suiri.service.command.support.state.settings.test;

import com.odeyalo.bot.suiri.service.command.steps.settings.test.ChangePreferredTestType;
import com.odeyalo.bot.suiri.service.command.support.state.ImMemoryStateRepository;
import org.springframework.stereotype.Component;

@Component
public class PreferredTestTypeChangeImMemoryStateRepository extends ImMemoryStateRepository<ChangePreferredTestType> {
}
