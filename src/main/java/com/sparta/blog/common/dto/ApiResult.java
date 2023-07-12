package com.sparta.blog.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString // 출력 시 각 field명을 포함시키는 annotation
public class ApiResult {
     // API result 반환을 위한 DTO
    // 성공 MSG와 status code(상태 코드)를 반환함
    private String msg;
    private int statusCode;

    @Builder // 해당 annotation이 적힌 field는 반드시 초기화가 되어있어야 한다.
    public ApiResult(String msg, int statusCode){
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
