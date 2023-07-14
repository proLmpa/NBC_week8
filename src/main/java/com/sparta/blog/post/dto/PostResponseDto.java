package com.sparta.blog.post.dto;

import com.sparta.blog.category.dto.CategoryResponseDto;
import com.sparta.blog.comment.dto.CommentResponseDto;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long postId;
    private String title;
    private String username;
    private String contents;
    private int likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CategoryResponseDto> categoryList;
    private List<CommentResponseDto> commentList;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.contents = post.getContents();
        this.likes = post.getPostLikes().size();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.categoryList = new ArrayList<>();
        this.commentList = new ArrayList<>();
    }

    public PostResponseDto(Post post, List<Comment> commentList) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.contents = post.getContents();
        this.likes = post.getPostLikes().size();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.commentList = commentList.stream().map(CommentResponseDto::new).toList();
        this.categoryList = post.getPostCategoryList().stream().map(postCategory -> new CategoryResponseDto(postCategory.getCategory())).toList();
    }
}
