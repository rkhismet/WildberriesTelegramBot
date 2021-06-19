package com.telegrambot.WildberriesBot.service.reply;

import com.telegrambot.WildberriesBot.cache.UserCache;
import com.telegrambot.WildberriesBot.model.ItemSubscription;
import com.telegrambot.WildberriesBot.reply.Reply;
import com.telegrambot.WildberriesBot.service.ItemSubscriptionRepositoryService;
import com.telegrambot.WildberriesBot.service.messages.CommonMessagesService;
import com.telegrambot.WildberriesBot.states.BotState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;


@Service
public class ItemDeleteReplyService implements Reply {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ItemSubscriptionRepositoryService subscriptionService;
    @Autowired
    CommonMessagesService messageService;
    @Autowired
    UserCache userCache;

    @Override
    public SendMessage sendMessage(Message message) {
        long chatId = message.getChatId();
        int userId = message.getFrom().getId();
        try {
            long a = Integer.parseInt(message.getText());
        } catch (RuntimeException e) {
            return messageService.sendWarningMessage(chatId, "reply.item.action.add.badRequest");
        }
        long itemId = Integer.parseInt(message.getText());
        Optional<ItemSubscription> itemSubscriptionOptional = subscriptionService.findByChatIdAndItemId(chatId, itemId);
        if (itemSubscriptionOptional.isEmpty()) {
            return messageService.sendMessage(chatId, "reply.item.action.delete.userError");
        }
        userCache.setUserBotState(userId, BotState.NULL_STATE);
        subscriptionService.deleteItemSubscription(itemSubscriptionOptional.get());
        logger.info("Chat Id = {} deleted item -> {}", chatId, itemId);
        return messageService.sendMessage(chatId, "reply.item.action.delete.success");
    }

    @Override
    public BotState getReplyName() {
        return BotState.UNSUBSCRIPTION_PROCESS;
    }
}
