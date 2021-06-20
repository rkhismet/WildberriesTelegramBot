package com.telegrambot.WildberriesBot.service.reply;

import com.telegrambot.WildberriesBot.cache.UserCache;
import com.telegrambot.WildberriesBot.model.ItemSubscription;
import com.telegrambot.WildberriesBot.reply.Reply;
import com.telegrambot.WildberriesBot.service.InfoRetrievingService;
import com.telegrambot.WildberriesBot.service.ItemSubscriptionRepositoryService;
import com.telegrambot.WildberriesBot.service.messages.CommonMessagesService;
import com.telegrambot.WildberriesBot.states.BotState;
import com.telegrambot.WildberriesBot.util.Emojis;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Service
public class ItemAddIdReplyService implements Reply {

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
        int userId = message.getFrom().getId();
        try {
            long a = Integer.parseInt(message.getText());
        } catch (RuntimeException e) {
            return messageService.sendWarningMessage(chatId, "reply.item.action.add.id.badRequest");
        }
        long itemId = Integer.parseInt(message.getText());
        Optional<ItemSubscription> itemSubscriptionOptional = subscriptionService.findByChatIdAndItemId(chatId, itemId);
        if (itemSubscriptionOptional.isPresent()) {
            subscriptionService.deleteItemSubscription(itemSubscriptionOptional.get());
        }
        ItemSubscription itemSubscription;
        try {
            itemSubscription = infoRetrievingService.retrieveItemByItemId(itemId);
            System.out.println(itemSubscription);
        } catch (HttpClientErrorException.BadRequest e) {
            return messageService.sendWarningMessage(chatId, "reply.item.action.add.badRequest");
        } catch (RuntimeException e) {
            return messageService.sendMessage(chatId, "reply.item.action.add.serverError", Emojis.FAIL_SERVER_MARK);
        }
        userCache.setUserBotState(userId, BotState.PRICE_PROCESS);
        userCache.setUserLastItem(userId, itemId);
        return messageService.sendSuccessMessage(message.getChatId(), "reply.item.action.add.id.success");
    }

    @Override
    public BotState getReplyName() {
        return BotState.SUBSCRIPTION_PROCESS;
    }

}
