package com.telegrambot.WildberriesBot.service;

import com.telegrambot.WildberriesBot.WildberriesTelegramBot;
import com.telegrambot.WildberriesBot.model.ItemSubscription;
import com.telegrambot.WildberriesBot.service.messages.CommonMessagesService;
import com.telegrambot.WildberriesBot.util.Emojis;
import com.vdurmont.emoji.Emoji;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class ScheduledPriceService {
    @Autowired
    ItemSubscriptionRepositoryService itemSubscriptionRepositoryService;
    @Autowired
    InfoRetrievingService infoRetrievingService;
    @Autowired
    WildberriesTelegramBot telegramBot;
    @Autowired
    CommonMessagesService commonMessages;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Scheduled(fixedRateString = "${subscriptions.processPeriod}")
    private void processAllItems() {
        logger.info("Start item process services");
        itemSubscriptionRepositoryService.allSubscription().forEach(this::handleItems);
        logger.info("End item process services");
    }
    public void handleItems(ItemSubscription itemSubscription) {
        long chatId = itemSubscription.getChatId();
        long price = itemSubscription.getPrice();
        long itemId = itemSubscription.getItemId();
        String itemName = itemSubscription.getName();
        try {
            ItemSubscription subscription = infoRetrievingService.retrieveItemByItemId(itemId);
            if (subscription == null) return;
            if (price < subscription.getPrice()) return;
            telegramBot.executeSendMessage(commonMessages.sendMessage(chatId, "reply.scheduled.notification.cheapPrice", Emojis.ATTENTION, "[" + itemId + "]" + "(https://kz.wildberries.ru/catalog/" + itemId + "/detail.aspx)", subscription.getPrice()));

        } catch (RuntimeException e) {
            logger.warn("Couldn't get price from Wildberries");
        }
    }
}
