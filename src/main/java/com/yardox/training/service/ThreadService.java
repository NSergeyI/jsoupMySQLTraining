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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ThreadService implements Runnable {

    String link;

    private static final Logger LOGGER = LogManager.getLogger(ThreadService.class);

    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private TagRepo tagRepo;

    public ThreadService() {
    }

    public ThreadService(String link) {
        this.link = link;
    }

    @Override
    public void run() {

        Document doc = null;
        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element newsHeadlines = doc.getElementsByClass("_content").first();
        String title = newsHeadlines.getElementsByClass("listHeader").text();
        String text = getText(newsHeadlines);
        Timestamp date = getTimestamp(newsHeadlines);
        Tag tag = getTag(newsHeadlines);
        Author author = getAuthor(newsHeadlines);
        News result = new News(title, date, text, link, tag, author);
        result = newsRepo.save(result);
    }

    private String getText(Element newsHeadlines) {
        String result = "";
        Elements elements = newsHeadlines.getElementsByTag("p");
        for (Element element : elements) {
            result = result + element.text() + "\n";
        }
        return result;
    }

    private Timestamp getTimestamp(Element element) {
        Timestamp result;
        if (element.getElementsByTag("time").text().isEmpty()) {
            result = Timestamp.valueOf(LocalDateTime.now());
        } else {
            String time = element.getElementsByTag("time").text();
//            LocalDateTime localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm"));
            LocalDateTime localDateTime = LocalDateTime.now();
            result = Timestamp.valueOf(localDateTime);
        }
        return result;
    }

    synchronized private Author getAuthor(Element element) {
        String name = "";
        Author result = authorRepo.findByName(name);
        if (result == null) {
            result = new Author(name);
            result = authorRepo.save(result);
        }
        return result;
    }

    synchronized private Tag getTag(Element element) {
        String name = element.getElementsByClass("categories").text();
        LOGGER.info(name);
        Tag result = tagRepo.findByName(name);
        if (result == null) {
            result = new Tag(name);
            result = tagRepo.save(result);
        }
        return result;
    }
}
