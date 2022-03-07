package com.bootcamp.reactive.blog.handlers;

import com.bootcamp.reactive.blog.entities.User;
import com.bootcamp.reactive.blog.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    @Autowired
    private UserServiceImpl service;

    public Mono<ServerResponse> login(ServerRequest request){
        return request.bodyToMono(User.class)
                .flatMap( credentials -> service.findByUsernameAndPassword(
                        credentials.getUsername(), credentials.getPassword())
                );
    }

    public Mono<ServerResponse> create(ServerRequest request){
        return request.bodyToMono(User.class)
                .flatMap( user -> service.createUser(user));
    }
}
