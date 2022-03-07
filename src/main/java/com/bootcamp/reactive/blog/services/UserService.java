package com.bootcamp.reactive.blog.services;

import com.bootcamp.reactive.blog.entities.User;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<ServerResponse> findByUsernameAndPassword(String username, String password);
    Mono<ServerResponse> createUser(User user);
}


