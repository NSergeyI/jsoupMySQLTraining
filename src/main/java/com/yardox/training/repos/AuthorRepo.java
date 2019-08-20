package com.yardox.training.repos;

import com.yardox.training.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepo extends CrudRepository<Author,Long> {
}
