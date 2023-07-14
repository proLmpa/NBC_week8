package com.sparta.blog.category.controller;

import com.sparta.blog.category.dto.CategoryRequestDto;
import com.sparta.blog.category.service.CategoryService;
import com.sparta.blog.common.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 1. 카테고리 생성
    @PostMapping("/category")
    public ApiResponseDto createCategory(CategoryRequestDto requestDto) {
        categoryService.createCategory(requestDto);
        return new ApiResponseDto(requestDto.getCategory() + " category registered", HttpStatus.OK.value());
    }

    // 2. 카테고리명 수정
    @PutMapping("/category")
    public ApiResponseDto updateCategory(CategoryRequestDto requestDto) {
        categoryService.updateCategory(requestDto);
        return new ApiResponseDto(requestDto.getCategory() + " category updated", HttpStatus.OK.value());
    }

    // 3. 카테고리 삭제
    @DeleteMapping("/category")
    public ApiResponseDto deleteCategory(CategoryRequestDto requestDto) {
        categoryService.deleteCategory(requestDto);
        return new ApiResponseDto(requestDto.getCategory() + " category deleted", HttpStatus.OK.value());
    }

    //
}
