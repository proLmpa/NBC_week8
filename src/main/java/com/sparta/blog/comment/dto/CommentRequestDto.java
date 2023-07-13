package com.sparta.blog.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long postId;
    private Long parentId = 0L;
    private String contents;
}
