package com.sparta.blog.post.repository;

import com.sparta.blog.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{
    Page<Post> findAllByPostCategoryList_CategoryId(Long categoryId, Pageable pageable);
}
