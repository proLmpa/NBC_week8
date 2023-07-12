package com.sparta.blog.like.comment.repository;

import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.like.comment.entity.CommentLike;
import com.sparta.blog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByLikeUserAndLikedComment(User user, Comment comment);
}
