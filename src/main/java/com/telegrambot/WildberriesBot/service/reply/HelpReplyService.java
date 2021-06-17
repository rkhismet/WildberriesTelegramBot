package com.telegrambot.WildberriesBot.service.reply;

import com.telegrambot.WildberriesBot.cache.UserCache;
import com.telegrambot.WildberriesBot.reply.Reply;
import com.telegrambot.WildberriesBot.service.messages.CommonMessagesService;
import com.telegrambot.WildberriesBot.service.messages.MainMenuKeyboardService;
import com.telegrambot.WildberriesBot.states.BotState;
import com.telegrambot.WildberriesBot.util.Emojis;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@NoArgsConstructor
public class HelpReplyService implements Reply {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MainMenuKeyboardService mainMenuKeyboardMessages;
    @Autowired
    UserCache userCache;
    @Autowired
    CommonMessagesService messageService;

    @Override
    public SendMessage sendMessage(Message message) {
        userCache.setUserBotState(message.getFrom().getId(), BotState.NULL_STATE);
        return mainMenuKeyboardMessages.sendMessage(message.getChatId(), messageService.getLocaleMessageObjects("reply.help.welcome", Emojis.ROBOT_FACE));
    }

    @Override
    public BotState getReplyName() {
        return BotState.SHOW_HELP_MENU;
    }
}
