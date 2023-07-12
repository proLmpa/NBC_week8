package com.sparta.blog.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlogErrorCode {
    INVALID_TOKEN(400, "토큰이 유효하지 않습니다."), // 필수
    INVALID_TYPE_VALUE(400, "입력된 값이 유효하지 않습니다."), // 필수 (확인)
    UNAUTHORIZED_USER(400, "작성자만 수정/삭제할 수 있습니다."), // 필수 (확인)
    IN_USED_USERNAME(400, "중복된 username 입니다."), // 필수 (확인)
    NOT_FOUND_POST(400, "요청한 게시글이 존재하지 않습니다."), // (확인)
    WRONG_PASSWORD(400, "비밀번호가 틀렸습니다."), //
    NOT_FOUND_COMMENT(400, "작성한 댓글을 찾을 수 없습니다."); // (확인)

    private final int errorCode;
    private final String errorMessage;
}
