package com.bootcamp.reactive.blog.services.impl;

import com.bootcamp.reactive.blog.entities.User;
import com.bootcamp.reactive.blog.repositories.UserRepository;
import com.bootcamp.reactive.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public Mono<ServerResponse> findByUsernameAndPassword(String username, String password) {
        return repository.findByUsernameAndPassword(username, password)
                .flatMap(user -> ServerResponse.ok().body(Mono.just(user), User.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> createUser(User user) {
        return repository.findByAuthorId(user.getAuthorId())
                .flatMap(foundUser -> ServerResponse.status(HttpStatus.CONFLICT).build())
                .switchIfEmpty(repository.save(user)
                        .flatMap( userSaved -> ServerResponse.status(201).body(Mono.just(userSaved), User.class)));
    }
}
