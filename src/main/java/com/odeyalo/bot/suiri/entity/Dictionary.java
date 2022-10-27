package com.odeyalo.bot.suiri.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private User user;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<DictionaryItem> items = new ArrayList<>();

    public void addToDictionary(DictionaryItem item) {
        this.items.add(item);
    }

    public void delete(DictionaryItem dictionaryItem) {
        items.removeIf(x -> x.getId().equals(dictionaryItem.getId()));
    }
}
