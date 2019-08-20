package com.yardox.training.service;

import com.yardox.training.repos.NewsRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

@Service
public class NewsService {

    private static final Logger LOGGER = LogManager.getLogger(NewsService.class);

    @Autowired
    private NewsRepo newsRepo;

    public String parse() {
        String result = "";
        Document doc = null;
        try {
            doc = Jsoup.connect("https://yarnovosti.com").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements newsHeadlines = doc.getElementsByClass("linkName");
        for (Element headline : newsHeadlines) {
            result = result + headline + "\n";
        }
        return result;
    }
}
