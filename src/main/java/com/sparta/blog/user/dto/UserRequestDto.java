package com.sparta.blog.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @NotBlank
    @Pattern(regexp="^[a-z0-9]{4,10}$", message="영소문자와 숫자(0~9)로 이뤄진 4자 이상 10자 이하의 값으로 이뤄졌습니다.")
    private String username;

    @NotBlank
    @Pattern(regexp="^[a-zA-Z0-9~`!@#$%^&*()-_+=]{8,15}$", message="영대소문자와 숫자(0~9) 및 특수문자(~`!@#$%^&*()-_+=)로 이뤄진 8자 이상 15자 이하의 값으로 이뤄졌습니다.")
    private String password;

    @NotBlank
    @Pattern(regexp="ADMIN|USER", message="권한은 ADMIN 혹은 USER만 입력 가능합니다.")
    private String role;
}