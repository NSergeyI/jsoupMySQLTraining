package com.yardox.training.repos;

import com.yardox.training.domain.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepo extends CrudRepository<Tag, Long> {
}
