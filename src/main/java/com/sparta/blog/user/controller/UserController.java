package com.sparta.blog.user.controller;

import com.sparta.blog.common.dto.ApiResult;
import com.sparta.blog.common.error.BlogErrorCode;
import com.sparta.blog.common.exception.BlogException;
import com.sparta.blog.user.dto.UserInfoDto;
import com.sparta.blog.user.dto.UserRequestDto;
import com.sparta.blog.user.entity.UserRoleEnum;
import com.sparta.blog.user.security.UserDetailsImpl;
import com.sparta.blog.user.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController // API 형식으로 반환하는 Controller 형식
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/signup")
    public ApiResult signup(@Valid @RequestBody UserRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외 처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for(FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            throw new BlogException(BlogErrorCode.INVALID_TYPE_VALUE, null);
        }

        userService.signup(requestDto);
        return new ApiResult("SUCCESS_SIGN_UP", HttpStatus.OK.value());
    }

    @GetMapping("/user-info")
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        UserRoleEnum role = userDetails.getUser().getRole();
        boolean isAdmin = (role == UserRoleEnum.ADMIN);

        return new UserInfoDto(username, isAdmin);
    }
}