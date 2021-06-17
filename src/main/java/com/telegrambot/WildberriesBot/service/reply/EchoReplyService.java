package com.telegrambot.WildberriesBot.service.reply;

import com.telegrambot.WildberriesBot.cache.UserCache;
import com.telegrambot.WildberriesBot.reply.Reply;
import com.telegrambot.WildberriesBot.service.messages.CommonMessagesService;
import com.telegrambot.WildberriesBot.service.messages.MainMenuKeyboardService;
import com.telegrambot.WildberriesBot.states.BotState;
import com.telegrambot.WildberriesBot.util.Emojis;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@NoArgsConstructor
public class EchoReplyService implements Reply {
    @Autowired
    MainMenuKeyboardService mainMenuKeyboard;
    @Autowired
    CommonMessagesService messageService;
    @Autowired
    UserCache userCache;
    @Override
    public SendMessage sendMessage(Message message) {
        userCache.setUserBotState(message.getFrom().getId(), BotState.NULL_STATE);
        return mainMenuKeyboard.sendMessage(message.getChatId(), messageService.getLocaleMessageEmoji("reply.echo.warning", Emojis.FAIL_USER_MARK));
    }

    @Override
    public BotState getReplyName() {
        return BotState.NULL_STATE;
    }
}
