package com.sparta.blog.comment.entity;

import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.common.entity.TimeStamped;
import com.sparta.blog.like.comment.entity.CommentLike;
import com.sparta.blog.post.entity.Post;
import com.sparta.blog.user.entity.User;
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
@Table(name = "comment")
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contents", nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment")
    private Comment parent;

    @Column(name = "child_comments")
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @Column(name = "like_count")
    @OneToMany(mappedBy = "comment", orphanRemoval = true)
    private List<CommentLike> commentLikes = new ArrayList<>();

    public Comment(CommentRequestDto requestDto, User user){
        this.contents = requestDto.getContents();
        this.user = user;
        this.parent = null;
        user.getComments().add(this);
    }

    public void addReplyList(Comment comment) {
        this.parent = comment;
        comment.getChildren().add(this);
    }

    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}
