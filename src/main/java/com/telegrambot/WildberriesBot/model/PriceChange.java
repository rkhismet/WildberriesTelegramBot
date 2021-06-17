package com.telegrambot.WildberriesBot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceChange {
    private int itemId;
    private String itemName;

    @JsonProperty(value = "priceUpdateTimeSeconds")
    private long priceLastUpdate;

    private int price;
}
