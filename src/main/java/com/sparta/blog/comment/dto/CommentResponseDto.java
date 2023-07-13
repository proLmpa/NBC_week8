package com.sparta.blog.comment.dto;

import com.sparta.blog.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String username;
    private String contents;
    private int likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.username = comment.getUser().getUsername();
        this.contents = comment.getContents();
        this.likes = comment.getCommentLikes().size();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.commentList = comment.getChildren().stream().map(CommentResponseDto::new).toList();
    }
}
