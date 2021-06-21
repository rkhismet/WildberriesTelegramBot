package com.telegrambot.WildberriesBot.service.reply;


import com.telegrambot.WildberriesBot.cache.UserCache;
import com.telegrambot.WildberriesBot.model.ItemSubscription;
import com.telegrambot.WildberriesBot.reply.Reply;
import com.telegrambot.WildberriesBot.service.InfoRetrievingService;
import com.telegrambot.WildberriesBot.service.ItemSubscriptionRepositoryService;
import com.telegrambot.WildberriesBot.service.messages.CommonMessagesService;
import com.telegrambot.WildberriesBot.states.BotState;
import com.telegrambot.WildberriesBot.util.Emojis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Service
public class ItemAddPriceReplyService implements Reply {
    @Autowired
    ItemSubscriptionRepositoryService subscriptionService;
    @Autowired
    CommonMessagesService messageService;
    @Autowired
    UserCache userCache;
    @Autowired
    InfoRetrievingService infoRetrievingService;

    @Override
    public SendMessage sendMessage(Message message) {
        long chatId = message.getChatId();
        long userId = message.getFrom().getId();
        try {
            long a = Integer.parseInt(message.getText());
        } catch (RuntimeException e) {
            return messageService.sendWarningMessage(chatId, "reply.item.action.add.price.badRequest");
        }
        long price = Integer.parseInt(message.getText());
        long itemId = userCache.getCurrentItem(userId);
        if (itemId == 0) {
            return messageService.sendMessage(chatId, "reply.item.action.add.price.unknownError");
        }
        ItemSubscription itemSubscription;
        try {
            itemSubscription = infoRetrievingService.retrieveItemByItemId(itemId);
        } catch (HttpClientErrorException.BadRequest e) {
            return messageService.sendWarningMessage(chatId, "reply.item.action.add.badRequest");
        } catch (RuntimeException e) {
            return messageService.sendMessage(chatId, "reply.item.action.add.serverError", Emojis.FAIL_SERVER_MARK);
        }
        Optional<ItemSubscription> itemSubscriptionOptional = subscriptionService.findByChatIdAndItemId(chatId, itemId);
        if (itemSubscriptionOptional.isPresent()) {
            subscriptionService.deleteItemSubscription(itemSubscriptionOptional.get());
        }
        String name = itemSubscription.getName();
        System.out.println(chatId + " " + itemId + " " + name + " " + price);
        subscriptionService.saveItemSubscription(new ItemSubscription(chatId, itemId, name, price));
        userCache.setUserBotState(userId, BotState.NULL_STATE);
        userCache.setUserLastItem(userId, 0L);
        return messageService.sendSuccessMessage(message.getChatId(), "reply.item.action.add.price.success");

    }

    @Override
    public BotState getReplyName() {
        return BotState.PRICE_PROCESS;
    }
}
