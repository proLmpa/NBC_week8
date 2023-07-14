package com.sparta.blog.user.entity;

import com.sparta.blog.category.entity.Category;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.like.comment.entity.CommentLike;
import com.sparta.blog.like.post.entity.PostLike;
import com.sparta.blog.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(name = "posts")
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @Column(name = "comments")
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "post_likes")
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();

    @Column(name = "comment_likes")
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<CommentLike> commentLikes = new ArrayList<>();

    @Column(name = "categories")
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();

    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}