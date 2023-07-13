package com.sparta.blog.like.post.entity;

import com.sparta.blog.post.entity.Post;
import com.sparta.blog.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post_like_table")
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public PostLike(User user, Post post) {
        this.user = user;
        this.post = post;
        user.getPostLikes().add(this);
        post.getPostLikes().add(this);
    }

    public void cancelLike(PostLike like) {
        user.getPostLikes().remove(like);
        post.getPostLikes().remove(like);
    }
}
