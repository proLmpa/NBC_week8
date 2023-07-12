package com.sparta.blog.comment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.post.entity.Post;
import com.sparta.blog.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "username", nullable = false)
    private String username;

    // @JsonBackReference // 원래는 @ManyToOne의 FETCHTYPE을 Lazy로 바꿔야 한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(CommentRequestDto requestDto, String username){
        this.content = requestDto.getContent();
        this.username = username;
    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
