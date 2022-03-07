package com.bootcamp.reactive.blog.repositories;

import com.bootcamp.reactive.blog.entities.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByUsernameAndPassword(String username, String password);
    Mono<User> findByAuthorId(String authorId);
}
