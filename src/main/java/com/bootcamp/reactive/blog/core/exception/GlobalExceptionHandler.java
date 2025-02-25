package com.bootcamp.reactive.blog.core.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();

        if (ex instanceof BlogNotFoundException) {
            var internalException= (BlogNotFoundException)ex;
            exchange.getResponse().setStatusCode(internalException.getStatus());
            DataBuffer dataBuffer = null;
            try {
                dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(new HttpError(internalException.getMessage()) ));
            } catch (JsonProcessingException e) {
                dataBuffer = bufferFactory.wrap("".getBytes());
            }
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));

        }

        if (ex instanceof AuthorExistsException) {
            var internalException= (AuthorExistsException)ex;
            exchange.getResponse().setStatusCode(internalException.getStatus());
            DataBuffer dataBuffer = null;
            try {
                dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(new HttpError(internalException.getMessage()) ));
            } catch (JsonProcessingException e) {
                dataBuffer = bufferFactory.wrap("".getBytes());
            }
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));

        }

        if (ex instanceof AuthorNotFoundException) {
            var internalException= (AuthorNotFoundException)ex;
            exchange.getResponse().setStatusCode(internalException.getStatus());
            DataBuffer dataBuffer = null;
            try {
                dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(new HttpError(internalException.getMessage()) ));
            } catch (JsonProcessingException e) {
                dataBuffer = bufferFactory.wrap("".getBytes());
            }
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));

        }

        if (ex instanceof AuthorIsNotOlderThan18Exception) {
            var internalException= (AuthorIsNotOlderThan18Exception)ex;
            exchange.getResponse().setStatusCode(internalException.getStatus());
            DataBuffer dataBuffer = null;
            try {
                dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(new HttpError(internalException.getMessage()) ));
            } catch (JsonProcessingException e) {
                dataBuffer = bufferFactory.wrap("".getBytes());
            }
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));

        }

        if (ex instanceof CustomError) {
            var internalException= (CustomError)ex;
            exchange.getResponse().setStatusCode(internalException.getStatus());
            DataBuffer dataBuffer = null;
            try {
                dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(new HttpError(internalException.getMessage()) ));
            } catch (JsonProcessingException e) {
                dataBuffer = bufferFactory.wrap("".getBytes());
            }
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));

        }

        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        DataBuffer dataBuffer = bufferFactory.wrap("Unknown error".getBytes());
        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

    public class HttpError {

        private String message;

        HttpError(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
