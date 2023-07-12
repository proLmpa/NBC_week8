package com.sparta.blog.comment.entity;

import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.common.entity.TimeStamped;
import com.sparta.blog.like.comment.entity.CommentLike;
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
@Table(name = "comment")
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "username", nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "like_count")
    @OneToMany(mappedBy = "likedComment", orphanRemoval = true)
    private List<CommentLike> commentLikes = new ArrayList<>();

    public Comment(CommentRequestDto requestDto, String username){
        this.contents = requestDto.getContents();
        this.username = username;
    }

    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }

    public void registerLike(CommentLike commentLike) {
        this.commentLikes.add(commentLike);
        commentLike.setLikedComment(this);
    }

    public void cancelLike(CommentLike commentLike) {
        this.commentLikes.remove(commentLike);
    }
}
