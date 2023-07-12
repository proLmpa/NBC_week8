package com.sparta.blog.common.exception;

import com.sparta.blog.common.error.BlogErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST) // response로 들어가는 에러 코드를 400으로 통일
public class BlogException extends RuntimeException {
    private final BlogErrorCode blogErrorCode;

    public BlogException(BlogErrorCode blogErrorCode, Throwable cause) {
        super(blogErrorCode.getErrorMessage(), cause, false, false);
        this.blogErrorCode = blogErrorCode;
    }
}
