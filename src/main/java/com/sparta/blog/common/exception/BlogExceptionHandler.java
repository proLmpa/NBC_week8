package com.sparta.blog.common.exception;

import com.sparta.blog.common.error.BlogErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class BlogExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { BlogException.class })
    protected ResponseEntity<BlogErrorResponse> handleCustomException(BlogException e) {
        return BlogErrorResponse.toResponseEntity(e.getBlogErrorCode());
    }
}
