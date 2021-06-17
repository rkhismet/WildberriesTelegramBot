package com.telegrambot.WildberriesBot.service.messages;

import com.telegrambot.WildberriesBot.util.Emojis;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@NoArgsConstructor
@Service
public class CommonMessagesService {

    @Autowired
    LocaleMessagesService localeMessages;

    public SendMessage sendMessage(Long chatId, String message) {
        return createSendMessage(Long.toString(chatId), localeMessages.getMessage(message));
    }

    public SendMessage sendMessage(Long chatId, String message, Object... args) {
        return createSendMessage(Long.toString(chatId), getLocaleMessageObjects(message, args));
    }

    public SendMessage sendSuccessMessage(Long chatId, String message) {
        return createSendMessage(Long.toString(chatId), getLocaleMessageEmoji(message, Emojis.SUCCESS_MARK));
    }

    public SendMessage sendWarningMessage(long chatId, String message) {
        return createSendMessage(Long.toString(chatId), getLocaleMessageEmoji(message, Emojis.FAIL_USER_MARK));
    }

    private SendMessage createSendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.enableMarkdown(true);
        sendMessage.disableWebPagePreview();
        return sendMessage;
    }

    public String getLocaleMessageObjects(String message, Object... args) {
        return localeMessages.getMessage(message, args);
    }

    public String getLocaleMessageEmoji(String message, Emojis emoji) {
        return localeMessages.getMessage(message, emoji);
    }

}
