package com.yardox.training.repos;

import com.yardox.training.domain.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepo extends CrudRepository<News, Long> {
    News findByLink(String link);
}
