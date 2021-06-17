package com.telegrambot.WildberriesBot.service.reply;

import com.telegrambot.WildberriesBot.cache.UserCache;
import com.telegrambot.WildberriesBot.reply.Reply;
import com.telegrambot.WildberriesBot.service.messages.CommonMessagesService;
import com.telegrambot.WildberriesBot.service.messages.MainMenuKeyboardService;
import com.telegrambot.WildberriesBot.states.BotState;
import com.telegrambot.WildberriesBot.util.Emojis;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuReplyService implements Reply {

    @Autowired
    MainMenuKeyboardService mainMenuKeyboardMessages;
    @Autowired
    CommonMessagesService messageService;
    @Autowired
    UserCache userCache;

    @Override
    public SendMessage sendMessage(Message message) {
        userCache.setUserBotState(message.getFrom().getId(), BotState.NULL_STATE);
        return mainMenuKeyboardMessages.sendMessage(message.getChatId(), messageService.getLocaleMessageEmoji("reply.main.welcome", Emojis.ROBOT_FACE));
    }

    @Override
    public BotState getReplyName() {
        return BotState.SHOW_MAIN_MENU;
    }
}
