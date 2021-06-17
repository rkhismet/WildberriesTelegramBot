package com.telegrambot.WildberriesBot.service.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocaleMessagesService {
    private final Locale locale;
    private final MessageSource messageSource;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public LocaleMessagesService (@Value("${localeTag}") String localeTag, MessageSource messageSource) {
        this.locale = Locale.getDefault();
        this.messageSource = messageSource;
    }

    public String getMessage(String message) {
        return messageSource.getMessage(message, null, locale);
    }

    public String getMessage(String message, Object... args) {
        return messageSource.getMessage(message, args, locale);
    }
}
