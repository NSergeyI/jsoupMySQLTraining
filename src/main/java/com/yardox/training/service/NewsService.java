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
import java.util.concurrent.Exchanger;

@Service
public class NewsService {
    private static final Logger LOGGER = LogManager.getLogger(NewsService.class);

    public String getData() {
        LOGGER.info("start");
        String result = "+++";
        return result;
    }
}
