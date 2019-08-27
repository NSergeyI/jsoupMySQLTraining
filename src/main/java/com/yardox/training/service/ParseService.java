package com.yardox.training.service;

import com.yardox.training.domain.Author;
import com.yardox.training.domain.News;
import com.yardox.training.domain.Tag;
import com.yardox.training.repos.AuthorRepo;
import com.yardox.training.repos.NewsRepo;
import com.yardox.training.repos.TagRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.Exchanger;

@Service
public class ParseService {
    private static final Logger LOGGER = LogManager.getLogger(NewsService.class);

    Exchanger<News> ex = new Exchanger<>();

    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private TagRepo tagRepo;

    public void startParse(){
        LOGGER.info("start");
        Document doc = getDocument("https://yarnovosti.com");
        Elements newsHeadlines = doc.getElementsByTag("article");
        for (Element headline : newsHeadlines) {
            getNews(headline);
        }
        saveNewsFromThreads();
    }

    private void saveNewsFromThreads(){
        LOGGER.info("start");
        News news = new News();
        while(news!=null){
            try {
                news = ex.exchange(news);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (news != null) {
                news.setAuthor(getAuthor(news.getAuthor().getName()));
                news.setTag(getTag(news.getTag().getName()));
            }
            news = newsRepo.save(news);
            LOGGER.info("finish " + news.getTitle());
        }
        LOGGER.info("finish");
    }

    private News getNews(Element element) {
        LOGGER.info("start");
        String link = element.getElementsByClass("linkName").first().getElementsByTag("a").attr("href");
        Runnable runnable = new ThreadService(link, ex);
        News news = null;
        new Thread(runnable).start();
        return null;
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

    private Author getAuthor(String name) {
        Author result = authorRepo.findByName(name);
        if (result == null) {
            result = new Author(name);
            result = authorRepo.save(result);
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
