package com.telegrambot.WildberriesBot.handler;

import com.telegrambot.WildberriesBot.cache.UserCache;
import com.telegrambot.WildberriesBot.service.reply.EchoReplyService;
import com.telegrambot.WildberriesBot.reply.Reply;
import com.telegrambot.WildberriesBot.states.BotState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UpdateHandler {
    @Autowired
    UserCache  userCache;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    Map<BotState, Reply> replyToBotState = new HashMap<>();

    public UpdateHandler(List<Reply> replyList) {
        replyList.forEach(reply -> replyToBotState.put(reply.getReplyName(), reply));
    }

    public SendMessage handler(Message message) {
        User user = message.getFrom();
        String text = message.getText();
        BotState botState = switch (text) {
            case "/help", "❓Help" -> BotState.SHOW_HELP_MENU;
            case "/add_item", "\uD83D\uDED2Добавьте товар" -> BotState.SUBSCRIPTION_START;
            case "/delete_item", "\uD83D\uDDD1Удалите товар" -> BotState.UNSUBSCRIPTION_START;
            case "/list_items", "\uD83D\uDCDCShow Profile List" -> BotState.SHOW_CART_LIST;
            default -> userCache.getCurrentBotState(user.getId());
        };
        userCache.setUserBotState(user.getId(), botState);
        Reply reply = handleReplyToBotState(botState);
        return reply.sendMessage(message);
    }
    private Reply handleReplyToBotState(BotState botState) {
        if (isProfileUpdate(botState)) {
            return replyToBotState.get(BotState.SUBSCRIPTION_START);
        }
        if (botState == null) {
            return new EchoReplyService();
        }
        for (BotState a : replyToBotState.keySet()) {
            System.out.println("states: " + a);

        }
        System.out.println(botState);
        System.out.println("cheto neto3");
        return replyToBotState.get(botState);
    }

    private boolean isProfileUpdate(BotState botState) {
        if (botState == BotState.SUBSCRIPTION_START || botState == BotState.UNSUBSCRIPTION_START) {
            System.out.println("cheto neto");
            return true;
        }
        return false;
    }
}
