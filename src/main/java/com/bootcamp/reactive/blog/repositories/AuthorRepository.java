package com.bootcamp.reactive.blog.repositories;

import com.bootcamp.reactive.blog.entities.Author;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author,String> {
}
