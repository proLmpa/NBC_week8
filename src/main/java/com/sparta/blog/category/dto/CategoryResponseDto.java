package com.sparta.blog.category.dto;

import com.sparta.blog.category.entity.Category;
import lombok.Getter;

@Getter
public class CategoryResponseDto {
    private String category;

    public CategoryResponseDto(Category category) {
        this.category = category.getCategory();
    }
}
