package com.telegrambot.WildberriesBot.service.messages;

import com.telegrambot.WildberriesBot.util.Emojis;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainMenuKeyboardService {
    public SendMessage sendMessage(long chatId, String message) {
        ReplyKeyboardMarkup keyboardMarkup = mainMenu();
        return createMessageKeyboard(chatId, message, keyboardMarkup);
    }
    public ReplyKeyboardMarkup mainMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setOneTimeKeyboard(false);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setSelective(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        keyboardRowList.add(addButton(Emojis.CART + "Добавить товар"));
        keyboardRowList.add(addButton(Emojis.TRASH_BIN + "Удалить товар"));
        keyboardRowList.add(addButton(Emojis.SCROLL + "Показать все товары"));
        keyboardRowList.add(addButton(Emojis.QUESTION_MARK + "Помощь"));
        for (KeyboardRow a : keyboardRowList) {
            System.out.println(a);
        }
        keyboardMarkup.setKeyboard(keyboardRowList);
        return keyboardMarkup;
    }
    private KeyboardRow addButton(String buttonText) {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton(buttonText));
        return keyboardRow;
    }
    public SendMessage createMessageKeyboard(long chatId, String message, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}
