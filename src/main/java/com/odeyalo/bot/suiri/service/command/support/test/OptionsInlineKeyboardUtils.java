package com.odeyalo.bot.suiri.service.command.support.test;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class OptionsInlineKeyboardUtils {

    public static List<List<InlineKeyboardButton>> getOptionsList(UserDictionaryKnowledgeTest question) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (String option : question.getOptions()) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(InlineKeyboardButton.builder()
                            .text(option)
                            .callbackData("failed")
                            .build());
            rowsInline.add(rowInline);
        }
        return rowsInline;
    }


    public static void correctAnswerSet(List<List<InlineKeyboardButton>> rowsInline, UserDictionaryKnowledgeTest firstQuestion) {
        String correctAnswer = firstQuestion.getCorrectAnswer();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(InlineKeyboardButton.builder().text(correctAnswer).callbackData("success").build());
        rowsInline.add(0, rowInline);
    }


    public static ArrayList<List<InlineKeyboardButton>> getKeyboard(UserDictionaryKnowledgeTest question) {
        List<List<InlineKeyboardButton>> rowsInline = getOptionsList(question);
        OptionsInlineKeyboardUtils.correctAnswerSet(rowsInline, question);
        ArrayList<List<InlineKeyboardButton>> keyboard = getKeyboardWithoutDuplicates(rowsInline);
        Collections.shuffle(rowsInline);
        return keyboard;
    }

    private static ArrayList<List<InlineKeyboardButton>> getKeyboardWithoutDuplicates(List<List<InlineKeyboardButton>> rowsInline) {
        return new ArrayList<>(new HashSet<>(rowsInline));
    }
}
