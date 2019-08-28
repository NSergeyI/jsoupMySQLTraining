package com.yardox.training.service;

import com.yardox.training.domain.News;
import com.yardox.training.repos.NewsRepo;
import com.yardox.training.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
    private static final Logger LOGGER = LogManager.getLogger(NewsService.class);

    @Autowired
    NewsRepo newsRepo;

    public Iterable<News> getData() {
        LOGGER.info("start");
        String result = "";
        Iterable<News> news = newsRepo.findAll();
        for (News article : news) {
            result = result + news + "\n"+ "\n"+ "\n";
        }
        return news;
    }

    public String getJsonData() {
        LOGGER.info("start");
        JsonUtil jsonUtil = new JsonUtil();
        String result = jsonUtil.getJson(newsRepo.findAll());
        return result;
    }
}
