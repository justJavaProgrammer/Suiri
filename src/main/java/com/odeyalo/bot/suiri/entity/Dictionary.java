package com.odeyalo.bot.suiri.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;
    @ManyToMany(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<DictionaryItem> items = new ArrayList<>();

    public void addToDictionary(DictionaryItem item) {
        this.items.add(item);
    }

    public void delete(DictionaryItem dictionaryItem) {
        items.removeIf(x -> x.getId().equals(dictionaryItem.getId()));
    }
}
