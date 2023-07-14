package com.sparta.blog.category.controller;

import com.sparta.blog.category.dto.CategoryRequestDto;
import com.sparta.blog.category.service.CategoryService;
import com.sparta.blog.common.dto.ApiResponseDto;
import com.sparta.blog.user.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ApiResponseDto createCategory(@RequestBody CategoryRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        categoryService.createCategory(requestDto, userDetails.getUser());
        return new ApiResponseDto(requestDto.getCategory() + " category registered", HttpStatus.OK.value());
    }

    // 2. 카테고리명 수정
    @PutMapping("/category")
    public ApiResponseDto updateCategory(@RequestBody CategoryRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        categoryService.updateCategory(requestDto, userDetails.getUser());
        return new ApiResponseDto(requestDto.getCategory() + " category updated to " + requestDto.getNewCategory(), HttpStatus.OK.value());
    }

    // 3. 카테고리 삭제
    @DeleteMapping("/category")
    public ApiResponseDto deleteCategory(@RequestBody CategoryRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        categoryService.deleteCategory(requestDto, userDetails.getUser());
        return new ApiResponseDto(requestDto.getCategory() + " category deleted", HttpStatus.OK.value());
    }

    //
}
