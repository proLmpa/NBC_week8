package com.sparta.blog.post.service;

import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.comment.repository.CommentRepository;
import com.sparta.blog.common.error.BlogErrorCode;
import com.sparta.blog.common.exception.BlogException;
import com.sparta.blog.common.jwt.JwtUtil;
import com.sparta.blog.post.dto.PostRequestDto;
import com.sparta.blog.post.dto.PostResponseDto;
import com.sparta.blog.post.entity.Post;
import com.sparta.blog.post.repository.PostRepository;
import com.sparta.blog.user.entity.User;
import com.sparta.blog.user.entity.UserRoleEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    private final PostRepository  postRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    public PostService(PostRepository postRepository, CommentRepository commentRepository, JwtUtil jwtUtil) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.jwtUtil = jwtUtil;
    }

    // 게시글 작성하기 (요구사항.2)
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User user){
        // RequestDto -> Entity
        Post post = new Post(requestDto, user.getUsername());

        // DB 저장
        Post savedPost = postRepository.save(post);

        // Entity -> ResponseDto
        return new PostResponseDto(savedPost);
    }

    // 전체 게시글 작성 날짜 내림차 순으로 조회하기 (요구사항.1)
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map((Post post) -> new PostResponseDto(
                post, commentRepository.findAllByPostIdOrderByCreatedAtDesc(post.getId())
        )).toList();
    }

    // 선택한 게시글 조회하기 (요구사항.3)
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        // 해당 게시글이 DB에 존재하는지 확인
        Post post = findPost(id);
        List<Comment> commentList = commentRepository.findAllByPostIdOrderByCreatedAtDesc(id);

        return new PostResponseDto(post, commentList);
    }

    // 선택한 게시글 수정하기 (요구사항.4)
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {
        // 해당 게시글이 DB에 존재하는지 확인
        Post post = findPost(id);

        // 해당 사용자가 작성한 댓글 여부 혹은 관리자 여부 확인
        if(matchUser(post, user) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            // post 내용 수정
            post.update(requestDto);

            List<Comment> commentList = commentRepository.findAllByPostIdOrderByCreatedAtDesc(id);

            return new PostResponseDto(post, commentList);
        } else {
            throw new BlogException(BlogErrorCode.UNAUTHORIZED_USER, null);
        }
    }

    // 선택한 게시글 삭제하기 (요구사항.5)
    @Transactional
    public void deletePost(Long id, User user) {
        // 해당 게시글이 DB에 존재하는지 확인
        Post post = findPost(id);

        // 해당 사용자가 작성한 댓글 여부 혹은 관리자 여부 확인
        if(matchUser(post, user) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            // post 내용 삭제
            postRepository.delete(post);
        } else {
            throw new BlogException(BlogErrorCode.UNAUTHORIZED_USER, null);
        }
    }

    // 해당 게시글이 DB에 존재하는지 확인
    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new BlogException(BlogErrorCode.NOT_FOUND_POST, null));
    }

    public boolean matchUser(Post post, User user) {
        return post.getUsername().equals(user.getUsername());
    }
}
