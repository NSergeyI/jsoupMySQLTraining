package com.yardox.training.util;

import com.yardox.training.domain.News;
import com.yardox.training.domain.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;


public class ThreadUtil implements Runnable {

    private String link;

    private static final Logger LOGGER = LogManager.getLogger(ThreadUtil.class);

    Exchanger<News> exchanger;

    public ThreadUtil(String link, Exchanger<News> ex) {
        this.link = link;
        this.exchanger = ex;
    }

    @Override
    public void run() {
        LOGGER.info("start");
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
        Set<Tag> tags = getTags(newsHeadlines);
        News result = new News(title, date, text, link, tags);
        try {
            exchanger.exchange(result);
        } catch (InterruptedException e) {
            LOGGER.error("error send data to exchanger");
            e.printStackTrace();
        }
    }

    private String getText(Element newsHeadlines) {
        String result = "";
        Elements elements = newsHeadlines.getElementsByClass("news current news_content").first().getElementsByTag("p");
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
            String time = element.getElementsByClass("news current news_content").first().getElementsByTag("time").text();
            LocalDateTime localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm"));
            result = Timestamp.valueOf(localDateTime);
        }
        return result;
    }

    private Set<Tag> getTags(Element element) {
        Set<Tag> result = new HashSet<Tag>();
        String name;
        Elements elements = element.getElementsByClass("tags").first().getElementsByTag("a");
        for (Element tag : elements) {
            name = tag.text();
            result.add(new Tag(name));
        }
        return result;
    }
}
