package com.sparta.blog.like.post.repository;

import com.sparta.blog.like.post.entity.PostLike;
import com.sparta.blog.post.entity.Post;
import com.sparta.blog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByLikeUserAndLikedPost(User user, Post post);
}
