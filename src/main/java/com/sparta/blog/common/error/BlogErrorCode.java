package com.sparta.blog.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlogErrorCode {
    EXPIRED_TOKEN(400, "토큰이 만료되었습니다."),
    INVALID_TOKEN(400, "토큰이 유효하지 않습니다."), // 필수
    INVALID_TYPE_VALUE(400, "입력이 규정에 어긋나거나 일부 요소가 공백입니다."), // 필수 (확인)
    IN_USED_USERNAME(400, "중복된 username 입니다."), // 필수 (확인)
    WRONG_PASSWORD(400, "비밀번호가 틀렸습니다."), //
    USER_NOT_FOUND(400, "해당 사용자는 존재하지 않습니다."),
    POST_NOT_FOUND(400, "해당 게시글은 존재하지 않습니다."), // (확인)
    COMMENT_NOT_FOUND(400, "해당 댓글은 존재하지 않습니다."), // (확인)
    UNAUTHORIZED_USER(400, "작성자만 수정/삭제할 수 있습니다."); // 필수 (확인)

    private final int errorCode;
    private final String errorMessage;
}
