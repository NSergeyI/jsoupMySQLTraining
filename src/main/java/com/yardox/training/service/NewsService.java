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
        Document doc = null;
        try {
            doc = Jsoup.connect("https://yarnovosti.com").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements newsHeadlines = doc.getElementsByTag("article");
        for (Element headline : newsHeadlines) {
            result = result + getNews(headline) + "\n";
        }
        return result;
    }

    private News getNews(Element element) {
        String title = element.getElementsByClass("content").text();
        Timestamp date = new Timestamp(1000000);
        String text = element.getElementsByClass("caption").text();
        Tag tag = getTag(element);
        Author author = getAuthor(element);
        News result = new News(title, date, text, tag, author);
        result = newsRepo.save(result);
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
}
