package com.odeyalo.bot.suiri.service.command.steps.settings;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SettingsLanguagePropertiesConstants {
    public static final String DEFAULT_SETTINGS_MESSAGE_PROPERTY = "language.word.settings.default.message";
    public static final String NOTIFICATION_SETTINGS_PROPERTY = "language.settings.notification";
    public static final String ON_NOTIFICATION_DISABLED_PROPERTY = "language.settings.notification.message.on.disable";
    public static final String ON_NOTIFICATION_ENABLED_PROPERTY = "language.settings.notification.message.on.enable";
    public static final String PREFERRED_TEST_TYPE_VALUE = "language.settings.preferred.test.type.value";
    public static final String PREFERRED_TEST_TYPE_ON_START_MESSAGE = "language.settings.preferred.test.type.on.start";
    public static final String PREFERRED_TEST_TYPE_QUIZ_POLL = "language.settings.preferred.test.type.quiz.poll";
    public static final String PREFERRED_TEST_TYPE_UPDATABLE_MESSAGE = "language.settings.preferred.test.type.updatable.message";
    public static final String PREFERRED_TEST_TYPE_ON_CHANGED = "language.settings.preferred.test.type.on.changed.message";
    public static final String PREFERRED_TEST_TYPE_ON_FINISH = "language.settings.preferred.test.type.on.finish";
}
