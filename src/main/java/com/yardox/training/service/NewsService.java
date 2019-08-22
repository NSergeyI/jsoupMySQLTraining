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
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Service
public class NewsService {

    private static final Logger LOGGER = LogManager.getLogger(NewsService.class);

    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private TagRepo tagRepo;


    public String parse() {
        String result = "";
        Document doc = getDocument("https://yarnovosti.com");
        Elements newsHeadlines = doc.getElementsByTag("article");
        for (Element headline : newsHeadlines) {
            result = result + getNews(headline) + "\n";
        }
        return result;
    }

    private News getNews(Element element) {
        String link = element.getElementsByClass("linkName").first().getElementsByTag("a").attr("href");
        Document doc = getDocument(link);
        Element newsHeadlines = doc.getElementsByClass("news current").first();
        String title = newsHeadlines.getElementsByClass("listHeader").text();
        String text = getText(newsHeadlines);
        Timestamp date = getTimestamp(element);
//        element.getElementsByClass("caption").text();
        Tag tag = getTag(element);
        Author author = getAuthor(element);
        News result = new News(title, date, text, tag, author);
        result = newsRepo.save(result);
//        newsHeadlines.notifyAll();
        return result;
    }

    private String getText(Element newsHeadlines) {
        String result = "";
        Elements elements = newsHeadlines.getElementsByTag("p");
        for (Element element : elements) {
            result = result + element.text() + "\n";
        }
        return result;
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

    private Author getAuthor(Element element) {
        String name = "";
        Author result = new Author(name);
        result = authorRepo.save(result);
        return result;
    }

    private Tag getTag(Element element) {
        String name = element.getElementsByClass("categories").text();
        Tag result = new Tag(name);
        result = tagRepo.save(result);
        return result;
    }

    private Timestamp getTimestamp(Element element) {
        Timestamp result;
        if (element.getElementsByTag("time").text().isEmpty()) {
            result = Timestamp.valueOf(LocalDateTime.now());
        } else {
            String time = element.getElementsByTag("time").text();
            LocalDateTime localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm"));
            result = Timestamp.valueOf(localDateTime);
        }
        return result;
    }
}
