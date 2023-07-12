package com.sparta.blog.post.entity;

import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.common.entity.TimeStamped;
import com.sparta.blog.like.post.entity.PostLike;
import com.sparta.blog.post.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post") // 매핑할 테이블의 이름을 지정
public class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "comment_list")
    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Column(name = "like_count")
    @OneToMany(mappedBy = "likedPost", orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();

    public void addCommentList(Comment comment) {
        this.commentList.add(comment);
        comment.setPost(this);
    }

    public Post(PostRequestDto requestDto, String username) {
        this.title = requestDto.getTitle();
        this.username = username;
        this.contents = requestDto.getContents();
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void registerLike(PostLike postLike) {
        this.postLikes.add(postLike);
        postLike.setLikedPost(this);
    }

    public void cancelLike(PostLike postLike) {
        this.postLikes.remove(postLike);
    }
}
