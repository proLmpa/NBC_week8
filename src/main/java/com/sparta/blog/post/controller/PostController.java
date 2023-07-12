package com.sparta.blog.post.controller;

import com.sparta.blog.common.dto.ApiResponseDto;
import com.sparta.blog.post.dto.PostRequestDto;
import com.sparta.blog.post.dto.PostResponseDto;
import com.sparta.blog.post.service.PostService;
import com.sparta.blog.user.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성하기 (요구사항.2)
    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // UserDetails.getUser() : Authentication의 Principle
        return postService.createPost(requestDto, userDetails.getUser());
    }

    // 전체 게시글 작성 날짜 기준 내림차순으로 조회하기 (요구사항.1)
    @GetMapping("/post")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    // 선택한 게시글 조회하기 (요구사항.3)
    @GetMapping("/post/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 선택한 게시글 수정하기 (요구사항.4)
    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // UserDetails.getUser() : Authentication의 Principle
        return postService.updatePost(id, requestDto, userDetails.getUser());
    }

    // 선택한 게시글 삭제하기 (요구사항.5)
    @DeleteMapping("/post/{id}")
    public ApiResponseDto deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // UserDetails.getUser() : Authentication의 Principle
        postService.deletePost(id, userDetails.getUser());

        return new ApiResponseDto("SUCCESS_DELETE_POST", HttpStatus.OK.value());
    }

    // 선택한 게시글 좋아요 등록/해제
    @PostMapping("/post/{id}/like")
    public ApiResponseDto likePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.likePost(id, userDetails.getUser());
    }
}
