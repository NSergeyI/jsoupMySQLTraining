package com.yardox.training.service;

import com.yardox.training.domain.News;
import com.yardox.training.domain.Tag;
import com.yardox.training.repos.NewsRepo;
import com.yardox.training.repos.TagRepo;
import com.yardox.training.util.ThreadUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class ParseService {
    private static final Logger LOGGER = LogManager.getLogger(NewsService.class);

    Exchanger<News> ex = new Exchanger<>();

    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private TagRepo tagRepo;

    public void startParse() {
        LOGGER.info("start");
        Document doc = getDocument("https://yarnovosti.com");
        Elements newsHeadlines = doc.getElementsByTag("article");
        for (Element headline : newsHeadlines) {
            startThreadService(headline);
        }
        saveNewsFromThreads();
    }

    private void saveNewsFromThreads() {
        LOGGER.info("start");
        News news = new News();
        while (news != null) {
            try {
                news = ex.exchange(news, 3000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                news = null;
                LOGGER.info("end parse timeout");
            }
            if (news != null) {
                Set<Tag> tags = new HashSet<>();
                for (Tag tag : news.getTags()) {
                    tags.add(getTag(tag.getName()));
                }
                news.setTags(tags);
                checkAndSaveNews(news);
            }
        }
    }

    private News checkAndSaveNews(News input){
        News result = newsRepo.findByLink(input.getLink());
            if (result == null) {
                result = newsRepo.save(input);
            }
            return result;
    }

    private void startThreadService(Element element) {
        LOGGER.info("start");
        String link = element.getElementsByClass("linkName").first().getElementsByTag("a").attr("href");
        Runnable runnable = new ThreadUtil(link, ex);
        News news = null;
        new Thread(runnable).start();
    }

    private Document getDocument(String link) {
        Document result = null;
        try {
            result = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Tag getTag(String name) {
        Tag result = tagRepo.findByName(name);
        if (result == null) {
            result = new Tag(name);
            result = tagRepo.save(result);
        }
        return result;
    }
}
