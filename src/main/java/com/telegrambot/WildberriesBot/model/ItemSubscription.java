package com.telegrambot.WildberriesBot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ItemSubscription {
    @Id
    @GeneratedValue
    long id;

    long chatId;
    long itemId;
    String name;
    long price;

    public ItemSubscription(long chatId, long itemId, String name, long price) {
        this.chatId = chatId;
        this.itemId = itemId;
        this.name = name;
        this.price = price;
    }
}
