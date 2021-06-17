package com.telegrambot.WildberriesBot.util;

import com.vdurmont.emoji.EmojiParser;

public enum Emojis {
    CART(EmojiParser.parseToUnicode(":shopping_cart:")),
    SCROLL(EmojiParser.parseToUnicode(":scroll:")),
    FAIL_SERVER_MARK(EmojiParser.parseToUnicode(":x:")),
    TRASH_BIN(EmojiParser.parseToUnicode(":wastebasket:")),
    MONEY(EmojiParser.parseToUnicode(":moneybag:")),
    ROBOT_FACE(EmojiParser.parseToUnicode(":bot_face:")),
    FAIL_USER_MARK(EmojiParser.parseToUnicode(":no_good:")),
    QUESTION_MARK(EmojiParser.parseToUnicode(":question:")),
    ADVICE(EmojiParser.parseToUnicode(":information_source:")),
    NEW(EmojiParser.parseToUnicode(":new:")),
    SUCCESS_MARK(EmojiParser.parseToUnicode(":white_check_mark:")),
    ATTENTION(EmojiParser.parseToUnicode(":bangbang:"));

    private String emojiName;

    @Override
    public String toString() {
        return emojiName;
    }

    Emojis(String emojiName) {
        this.emojiName = emojiName;
    }
}
