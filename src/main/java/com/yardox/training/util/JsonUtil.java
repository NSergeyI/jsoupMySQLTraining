package com.yardox.training.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yardox.training.domain.News;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonUtil {

    private static final Logger LOGGER = LogManager.getLogger(JsonUtil.class);
    public String getJson(Iterable<News> news) {
        LOGGER.info("start");
        String result = "NOTHING";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(news);
        } catch (JsonProcessingException e) {
            LOGGER.error("error convert news to json");
            e.printStackTrace();
        }
        return result;
    }
}
