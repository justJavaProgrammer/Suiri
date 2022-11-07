package com.odeyalo.bot.suiri.service.command.support.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    /*
     * Answer to given question
     */
    private UserDictionaryKnowledgeTest question;
}
