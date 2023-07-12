package com.sparta.blog.common.constant;

// final 선언 시, 오버라이딩 불가능
public final class ProjConst {
    private ProjConst() {
        // 객체 생성을 금지함.
    }

    public static final String ADMIN_ROLE = "ADMIN";

    public static final String API_CALL_SUCCESS = "SUCCESS";
}
