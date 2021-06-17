package com.telegrambot.WildberriesBot.reply;

import com.telegrambot.WildberriesBot.states.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Reply {

    SendMessage sendMessage(Message message);

    BotState getReplyName();
}
