package com.telegrambot.WildberriesBot.service.reply;

import com.telegrambot.WildberriesBot.cache.UserCache;
import com.telegrambot.WildberriesBot.reply.Reply;
import com.telegrambot.WildberriesBot.service.messages.CommonMessagesService;
import com.telegrambot.WildberriesBot.states.BotState;
import com.telegrambot.WildberriesBot.states.ItemState;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@NoArgsConstructor
public class ItemAddReplyService implements Reply {
    @Autowired
    CommonMessagesService commonMessagesService;
    @Autowired
    UserCache userCache;
    @Override
    public SendMessage sendMessage(Message message) {
        long userId = message.getFrom().getId();
        ItemState userState = getUserState(userCache.getCurrentBotState(userId));
        userCache.setUserBotState(message.getFrom().getId(), (userState.equals(ItemState.SUBSCRIBE) ? BotState.SUBSCRIPTION_PROCESS : BotState.UNSUBSCRIPTION_PROCESS));
        String action = (userState.equals(ItemState.SUBSCRIBE) ? "добавить" : "убрать");
        return commonMessagesService.sendMessage(message.getChatId(), "reply.item.start.action", action);
    }

    private ItemState getUserState(BotState botState) {
        return switch (botState) {
            case SUBSCRIPTION_START -> ItemState.SUBSCRIBE;
            default -> ItemState.UNSUBSCRIBE;
        };
    }

    @Override
    public BotState getReplyName() {
        return BotState.SUBSCRIPTION_START;
    }
}
