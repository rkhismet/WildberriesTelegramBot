package com.telegrambot.WildberriesBot.service.reply;

import com.telegrambot.WildberriesBot.cache.UserCache;
import com.telegrambot.WildberriesBot.model.ItemSubscription;
import com.telegrambot.WildberriesBot.reply.Reply;
import com.telegrambot.WildberriesBot.service.InfoRetrievingService;
import com.telegrambot.WildberriesBot.service.ItemSubscriptionRepositoryService;
import com.telegrambot.WildberriesBot.service.messages.CommonMessagesService;
import com.telegrambot.WildberriesBot.states.BotState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;
import java.util.List;

@Service
public class ItemShowReplyService implements Reply {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ItemSubscriptionRepositoryService subscriptionService;
    @Autowired
    InfoRetrievingService retrievingService;
    @Autowired
    CommonMessagesService messageService;
    @Autowired
    UserCache userCache;

    @Override
    public SendMessage sendMessage(Message message) {
        long chatId = message.getChatId();
        int userId = message.getFrom().getId();
        List<ItemSubscription> itemList = subscriptionService.findByChatId(chatId);
        userCache.setUserBotState(userId, BotState.NULL_STATE);
        if (itemList.size() == 0) {
            return messageService.sendMessage(chatId, "reply.list.items.noData");
        }
        StringBuilder stringBuilder = new StringBuilder();
        itemList.forEach(item -> {
            String itemId = "[" + item.getItemId() + "]" + "(https://kz.wildberries.ru/catalog/" + item.getItemId() + "/detail.aspx)";
            String price = item.getPrice() + " ₽";
            String name = item.getName();
            long currentPrice = retrievingService.retrieveItemByItemId(item.getItemId()).getPrice();
            String curPrice = "Текущая цена: ";
            if (currentPrice != (long) 1e18) {
                curPrice = curPrice + currentPrice + " ₽";
            } else {
                curPrice = curPrice + "Нет на складе";
            }
            String line = "Товар: " + name + "\n" + "Желаемая цена: " + price + "\n" + curPrice + "\nАртикул: " + itemId;
            stringBuilder.append(line).append("\n\n");
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return messageService.sendMessage(chatId, "reply.list.items", stringBuilder.toString());
    }
    @Override
    public BotState getReplyName() {
        return BotState.SHOW_CART_LIST;
    }
}
