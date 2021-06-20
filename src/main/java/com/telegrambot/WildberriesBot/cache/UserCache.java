package com.telegrambot.WildberriesBot.cache;

import com.telegrambot.WildberriesBot.states.BotState;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserCache {

    private Map<Integer, BotState> UserBotState = new HashMap<>();
    private Map<Integer, Long> UserLastItem = new HashMap<>();

    public void setUserBotState(int userId, BotState botState) {
        UserBotState.put(userId, botState);
    }

    public void setUserLastItem(int userId, Long itemId) { UserLastItem.put(userId, itemId); }

    public BotState getCurrentBotState(int userId) {
        BotState botState = UserBotState.computeIfAbsent(userId, k -> BotState.NULL_STATE);
        return botState;
    }

    public Long getCurrentItem(int userId) {
        Long itemId = UserLastItem.computeIfAbsent(userId, k -> Long.valueOf(0));
        return itemId;
    }
}
