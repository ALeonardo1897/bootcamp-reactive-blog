package com.bootcamp.reactive.blog.services;

import com.bootcamp.reactive.blog.entities.Blog;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BlogService {
    Mono<Blog> findById(String id);
    Flux<Blog> findAll();
    Mono<ServerResponse> save(Blog blog);
    Mono<Void> delete(String id);
}
