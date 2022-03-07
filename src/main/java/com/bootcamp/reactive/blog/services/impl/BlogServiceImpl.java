package com.bootcamp.reactive.blog.services.impl;


import com.bootcamp.reactive.blog.core.exception.AuthorNotFoundException;
import com.bootcamp.reactive.blog.core.exception.CustomError;
import com.bootcamp.reactive.blog.entities.Blog;
import com.bootcamp.reactive.blog.repositories.BlogRepository;
import com.bootcamp.reactive.blog.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AuthorServiceImpl authorService;

    @Override
    public Mono<Blog> findById(String id) {
        return this.blogRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomError(HttpStatus.NOT_FOUND,"blog.notFound")));
    }

    @Override
    public Flux<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Mono<ServerResponse> save(Blog blog) {

        return authorService
                .findById(blog.getAuthorId())
                .flatMap( author -> {
                    if (!authorService.isOlderThan18(author)){
                        return Mono.error(new AuthorNotFoundException("author.isNotOlderThan18"));
                    } return Mono.just(author);
                })
                .then(blogRepository.findAllByAuthorId(blog.getAuthorId()).count())
                .flatMap( count -> {
                    if (count < 3) {
                        return blogRepository
                                .save(blog)
                                .then(ServerResponse.status(201)
                                        .body(Mono.just(blog),Blog.class));
                    }else{
                        return ServerResponse
                                .status(HttpStatus.CONFLICT)
                                .build();
                    }
                });

    }

    @Override
    public Mono<Void> delete(String id) {
        return this.blogRepository.findById(id)
                .doOnNext(b->{
                    System.out.println("doOnNext b = " + b);
                })
                .flatMap(blog-> this.blogRepository.delete(blog));

    }
}
