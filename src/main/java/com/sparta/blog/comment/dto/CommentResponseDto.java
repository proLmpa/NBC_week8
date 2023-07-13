package com.sparta.blog.comment.dto;

import com.sparta.blog.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String username;
    private String contents;
    private int likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.username = comment.getCommentAuthor().getUsername();
        this.contents = comment.getContents();
        this.likes = comment.getCommentLikes().size();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
