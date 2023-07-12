package com.sparta.blog.comment.service;

import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.comment.dto.CommentResponseDto;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.common.dto.ApiResponseDto;
import com.sparta.blog.common.error.BlogErrorCode;
import com.sparta.blog.common.exception.BlogException;
import com.sparta.blog.like.comment.entity.CommentLike;
import com.sparta.blog.like.comment.repository.CommentLikeRepository;
import com.sparta.blog.post.entity.Post;
import com.sparta.blog.user.entity.User;
import com.sparta.blog.common.jwt.JwtUtil;
import com.sparta.blog.comment.repository.CommentRepository;
import com.sparta.blog.post.repository.PostRepository;
import com.sparta.blog.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {
        // 선택한 게시글의 DB 존재 여부 확인
        Post post = findPost(requestDto.getPostId());

        // 댓글 등록 후 등록된 댓글 반환하기
        Comment comment = new Comment(requestDto, user.getUsername());
        post.addCommentList(comment);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        // 선택한 댓글이 DB이 존재하는지 확인
        Comment comment = findComment(commentId);

        // 해당 사용자가 작성한 댓글 여부 혹은 관리자 여부 확인
        if(matchUser(comment, user) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment.update(requestDto);

            return new CommentResponseDto(comment);
        } else {
            throw new BlogException(BlogErrorCode.UNAUTHORIZED_USER, null);
        }
    }

    @Transactional
    public void deleteComment(Long commentId, User user){
        // 선택한 댓글이 DB이 존재하는지 확인
        Comment comment = findComment(commentId);

        // 해당 사용자가 작성한 댓글 여부 혹은 관리자 여부 확인
        if(matchUser(comment, user) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            commentRepository.delete(comment);

        } else {
            throw new BlogException(BlogErrorCode.UNAUTHORIZED_USER, null);
        }
    }

    @Transactional
    public ApiResponseDto likeComment(Long id, User user) {
        Comment comment = findComment(id);

        CommentLike like = commentLikeRepository.findByLikeUserAndLikedComment(user, comment).orElse(null);

        if(like == null) {
            like = new CommentLike(user, comment);

            comment.registerLike(like);
            commentLikeRepository.save(like);
            return new ApiResponseDto("좋아요를 등록했습니다.", 200);
        } else {
            comment.cancelLike(like);
            commentLikeRepository.delete(like);
            return new ApiResponseDto("좋아요를 해제했습니다.", 200);
        }
    }

    // 해당 게시글이 DB에 존재하는지 확인
    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new BlogException(BlogErrorCode.NOT_FOUND_POST, null));
    }

    public Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new BlogException(BlogErrorCode.NOT_FOUND_COMMENT, null));
    }

    public boolean matchUser(Comment comment, User user) {
        return comment.getUsername().equals(user.getUsername());
    }

}
