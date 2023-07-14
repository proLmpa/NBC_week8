package com.sparta.blog.comment.service;

import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.comment.dto.CommentResponseDto;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.comment.repository.CommentRepository;
import com.sparta.blog.common.error.BlogErrorCode;
import com.sparta.blog.common.exception.BlogException;
import com.sparta.blog.like.comment.entity.CommentLike;
import com.sparta.blog.like.comment.repository.CommentLikeRepository;
import com.sparta.blog.post.entity.Post;
import com.sparta.blog.post.repository.PostRepository;
import com.sparta.blog.user.entity.User;
import com.sparta.blog.user.entity.UserRoleEnum;
import com.sparta.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {
        // userRepository -> User
        User foundUser = findUser(user);

        Long parentId = requestDto.getParentId();
        Comment comment;

        if(parentId == 0) {
            // 선택한 게시글의 DB 존재 여부 확인
            Post post = findPost(requestDto.getPostId());

            // Post에 새 Comment 등록 시
            comment = new Comment(requestDto, foundUser);
            post.addCommentList(comment);

            commentRepository.save(comment);
        } else {
            // 기존 Comment에 새 Comment 등록 시
            comment = findComment(parentId);
            Comment reply = new Comment(requestDto, foundUser);
            reply.addReplyList(comment);

            commentRepository.save(reply);
            comment = reply;
        }

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
    public String likeComment(Long id, User user) {
        // userRepository -> User
        User foundUser = findUser(user);
        Comment comment = findComment(id);

        CommentLike like = commentLikeRepository.findByUserAndComment(foundUser, comment).orElse(null);

        if(like == null) {
            like = new CommentLike(foundUser, comment);

            commentLikeRepository.save(like);
            return "좋아요를 등록했습니다.";
        } else {
            like.cancelLike(like);

            commentLikeRepository.delete(like);
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

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new BlogException(BlogErrorCode.COMMENT_NOT_FOUND, null));
    }

    private boolean matchUser(Comment comment, User user) {
        return comment.getUser().getUsername().equals(user.getUsername());
    }
}
