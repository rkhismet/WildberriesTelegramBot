package com.telegrambot.WildberriesBot.service;

import com.telegrambot.WildberriesBot.model.ItemSubscription;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@Service
public class InfoRetrievingService {
    String wildberriesUrl = "https://kz.wildberries.ru/catalog/";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ItemSubscription retrieveItemByItemId(long itemId) {
        ItemSubscription itemSubscription = null;
        try {
            Document doc = Jsoup.connect(wildberriesUrl + itemId + "/detail.aspx").get();
            String itemPrice = doc.getElementsByClass("final-cost").text();
            StringBuilder sb = new StringBuilder();
            itemPrice.chars().mapToObj(i -> (char) i).filter(Character::isDigit).forEach(sb::append);
            itemPrice = sb.toString();
            String itemName = doc.getElementsByClass("brand-and-name j-product-title").text();
            itemSubscription.setItemId(itemId);
            itemSubscription.setName(itemName);
            if (sb.length() == 0) {
                itemSubscription.setPrice((long) 1e18);
            } else {
                itemSubscription.setPrice(Integer.parseInt(itemPrice));
            }

        } catch (HttpClientErrorException.TooManyRequests e) {
            logger.warn("Too many requests to Wildberries");
        } catch (HttpClientErrorException.BadRequest e) {
            logger.info("Non existent item{}", itemId);
            throw e;
        } catch (IOException e) {
            logger.info ("Jsoup problems");
            throw new RuntimeException("Jsouuup");
        }
        return itemSubscription;
    }
}
