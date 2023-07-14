package com.sparta.blog.postCategory.repository;

import com.sparta.blog.category.entity.Category;
import com.sparta.blog.post.entity.Post;
import com.sparta.blog.postCategory.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
    Optional<PostCategory> findByPostAndCategory(Post post, Category category);
}
