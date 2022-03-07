package com.bootcamp.reactive.blog.services.impl;

import com.bootcamp.reactive.blog.core.exception.CustomError;
import com.bootcamp.reactive.blog.entities.Post;
import com.bootcamp.reactive.blog.repositories.BlogRepository;
import com.bootcamp.reactive.blog.repositories.PostRepository;
import com.bootcamp.reactive.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BlogServiceImpl blogService;

    @Override
    public Mono<Post> save(Post post) {

        return blogService.findById(post.getBlogId())
                .flatMap(blog -> existAnotherPostToday(blog.getId()))
                .flatMap(existAnotherPostToday -> {
                    if(existAnotherPostToday){
                        return Mono.error(
                                new CustomError(
                                        HttpStatus.CONFLICT,
                                        "post.existAnotherPostToday"));
                    }
                    return postRepository.save(post);
                });
    }

    @Override
    public Flux<Post> findAll() {
        return this.postRepository.findAll();
    }

    private Mono<Boolean> existAnotherPostToday(String blogId){

        return postRepository.findFirstByBlogIdOrderByDateDesc(blogId)
                .flatMap( post -> {
                    long diffInMillies = Math.abs(new Date().getTime() - post.getDate().getTime());
                    long diff = TimeUnit.MILLISECONDS.toDays(diffInMillies);

                    return diff > 1 ? Mono.just(false) : Mono.just(true);
                }).switchIfEmpty(Mono.just(false));

    }
}
