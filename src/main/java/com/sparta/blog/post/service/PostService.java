package com.sparta.blog.post.service;

import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.comment.repository.CommentRepository;
import com.sparta.blog.common.dto.ApiResponseDto;
import com.sparta.blog.common.error.BlogErrorCode;
import com.sparta.blog.common.exception.BlogException;
import com.sparta.blog.common.jwt.JwtUtil;
import com.sparta.blog.like.post.entity.PostLike;
import com.sparta.blog.like.post.repository.PostLikeRepository;
import com.sparta.blog.post.dto.PostRequestDto;
import com.sparta.blog.post.dto.PostResponseDto;
import com.sparta.blog.post.entity.Post;
import com.sparta.blog.post.repository.PostRepository;
import com.sparta.blog.user.entity.User;
import com.sparta.blog.user.entity.UserRoleEnum;
import com.sparta.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository  postRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;
    private final JwtUtil jwtUtil;

    // 게시글 작성하기 (요구사항.2)
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User user){
        // userRepository -> User
        User foundUser = findUser(user);

        // RequestDto -> Entity
        Post post = new Post(requestDto, foundUser);

        // DB 저장
        Post savedPost = postRepository.save(post);

        // Entity -> ResponseDto
        return new PostResponseDto(savedPost);
    }

    // 페이징, 정렬 기능을 이용한 게시글 조회
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPosts(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> postPage = postRepository.findAll(pageable);

        return postPage.map((Post post) -> new PostResponseDto(
                post, commentRepository.findAllByPostIdOrderByCreatedAtDesc(post.getId())
        ));
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

    @Transactional
    public String likePost(Long id, User user) {
        // userRepository -> User
        User foundUser = findUser(user);
        Post post = findPost(id);

        PostLike like = postLikeRepository.findByUserAndPost(foundUser, post).orElse(null);

        if(like == null){
            like = new PostLike(foundUser, post);

            postLikeRepository.save(like);
            return "좋아요를 등록했습니다.";
        } else {
            like.cancelLike(like);

            postLikeRepository.delete(like);
            return "좋아요를 해제했습니다.";
        }
    }

    private User findUser(User user) {
        return userRepository.findByUsername(user.getUsername()).orElseThrow(() ->
                new BlogException(BlogErrorCode.USER_NOT_FOUND, null));
    }

    // 해당 게시글이 DB에 존재하는지 확인
    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new BlogException(BlogErrorCode.POST_NOT_FOUND, null));
    }

    private boolean matchUser(Post post, User user) {
        return post.getUser().getUsername().equals(user.getUsername());
    }
}
