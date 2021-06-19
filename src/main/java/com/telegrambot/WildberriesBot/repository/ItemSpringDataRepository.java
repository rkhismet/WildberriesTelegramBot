package com.telegrambot.WildberriesBot.repository;

import com.telegrambot.WildberriesBot.model.ItemSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemSpringDataRepository extends JpaRepository<ItemSubscription, Long> {
    List<ItemSubscription> findByChatId(long chatId);
    List<ItemSubscription> findByChatIdAndItemId(long chatId, long itemId);
}
