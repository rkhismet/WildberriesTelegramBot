package com.telegrambot.WildberriesBot.cache;

import com.telegrambot.WildberriesBot.states.BotState;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserCache {

    private Map<Long, BotState> UserBotState = new HashMap<>();
    private Map<Long, Long> UserLastItem = new HashMap<>();

    public void setUserBotState(Long userId, BotState botState) {
        UserBotState.put(userId, botState);
    }

    public void setUserLastItem(Long userId, Long itemId) { UserLastItem.put(userId, itemId); }

    public BotState getCurrentBotState(Long userId) {
        BotState botState = UserBotState.computeIfAbsent(userId, k -> BotState.NULL_STATE);
        return botState;
    }

    public Long getCurrentItem(Long userId) {
        Long itemId = UserLastItem.computeIfAbsent(userId, k -> Long.valueOf(0));
        return itemId;
    }
}
