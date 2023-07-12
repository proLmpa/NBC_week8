package com.sparta.blog.like.comment.entity;

import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment_like_table")
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User likeUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment likedComment;

    public CommentLike(User user, Comment comment) {
        this.likeUser = user;
        this.likedComment = comment;
    }
}