package com.telegrambot.WildberriesBot.service;


import com.telegrambot.WildberriesBot.model.ItemSubscription;
import com.telegrambot.WildberriesBot.repository.ItemSpringDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemSubscriptionRepositoryService {
    @Autowired
    ItemSpringDataRepository itemSpringDataRepository;

    public Optional<ItemSubscription> findByChatIdAndItemId(long chatId, long itemId) {
        return itemSpringDataRepository.findByChatIdAndItemId(chatId, itemId).stream().findFirst();
    }
    public List<ItemSubscription> findByChatId(long chatId) {
        return itemSpringDataRepository.findByChatId(chatId);
    }
    public void saveItemSubscription(ItemSubscription itemSubscription) {
        itemSpringDataRepository.save(itemSubscription);
    }

    public void deleteItemSubscription(ItemSubscription itemSubscription) {
        itemSpringDataRepository.delete(itemSubscription);
    }

    public List<ItemSubscription> allSubscription() {
        return itemSpringDataRepository.findAll();
    }
}
