package com.odeyalo.bot.suiri.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DictionaryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String originalText;
    @Column(nullable = false)
    private String translatedText;
    private String picture;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Dictionary dictionary;
}
