package com.sparta.blog.category.service;

import com.sparta.blog.category.dto.CategoryRequestDto;
import com.sparta.blog.category.entity.Category;
import com.sparta.blog.category.repository.CategoryRepository;
import com.sparta.blog.common.error.BlogErrorCode;
import com.sparta.blog.common.exception.BlogException;
import com.sparta.blog.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // 1. 카테고리 등록
    @Transactional
    public void createCategory(CategoryRequestDto requestDto, User user) {
        Category category = findCategory(requestDto.getCategory());

        if(category == null) {
            category = new Category(requestDto.getCategory(), user);

            categoryRepository.save(category);
        } else {
            throw new BlogException(BlogErrorCode.CATEGORY_ALREADY_EXISTS, null);
        }
    }

    // 2. 카테고리 수정
    @Transactional
    public void updateCategory(CategoryRequestDto requestDto, User user) {
        Category category = findCategory(requestDto.getCategory());

        if (category != null && matchUser(category, user)) {
            category.setCategory(requestDto.getNewCategory());
        } else if (category == null) {
            throw new BlogException(BlogErrorCode.CATEGORY_NOT_FOUND, null);
        } else {
            throw new BlogException(BlogErrorCode.UNAUTHORIZED_USER, null);
        }
    }

    // 3. 카테고리 삭제
    @Transactional
    public void deleteCategory(CategoryRequestDto requestDto, User user) {
        Category category = findCategory(requestDto.getCategory());

        if (category != null && matchUser(category, user)) {
            categoryRepository.delete(category);
        } else if (category == null) {
            throw new BlogException(BlogErrorCode.CATEGORY_NOT_FOUND, null);
        } else {
            throw new BlogException(BlogErrorCode.UNAUTHORIZED_USER, null);
        }
    }

    private Category findCategory(String category) {
        return categoryRepository.findByCategory(category).orElse(null);
    }

    private boolean matchUser(Category category, User user) {
        return category.getUser().getUsername().equals(user.getUsername());
    }
}
