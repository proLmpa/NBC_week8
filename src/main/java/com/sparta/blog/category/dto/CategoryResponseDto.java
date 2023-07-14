package com.sparta.blog.category.dto;

import com.sparta.blog.category.entity.Category;
import lombok.Getter;

@Getter
public class CategoryResponseDto {
    private Long id;
    private String category;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.category = category.getCategory();
    }
}
