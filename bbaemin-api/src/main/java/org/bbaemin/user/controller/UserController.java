package org.bbaemin.user.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.config.exception.BindException;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.user.controller.request.JoinRequest;
import org.bbaemin.user.controller.request.UpdateUserInfoRequest;
import org.bbaemin.user.controller.response.UserResponse;
import org.bbaemin.user.service.UserService;
import org.bbaemin.user.vo.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 리스트
    @GetMapping
    public ApiResult<List<UserResponse>> listUser() {
        List<UserResponse> userList = userService.getUserList().stream()
                .map(UserResponse::new).collect(Collectors.toList());
        return ApiResult.ok(userList);
    }

    // 회원 조회
    @GetMapping("/{userId}")
    public ApiResult<UserResponse> getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        return ApiResult.ok(new UserResponse(user));
    }

    // 회원 등록
    @PostMapping
    public ApiResult<UserResponse> join(@Validated @RequestBody JoinRequest joinRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // TODO - CHECK : org.springframework.validation.BindException
            // validation 라이브러리에서 제공하는 BindException을 사용하는 것이 좋은가?
            // checked 예외 vs unchecked 예외
            throw new BindException(bindingResult);
        }

        User user = userService.join(
                User.builder()
                        .email(joinRequest.getEmail())
                        .nickname(joinRequest.getNickname())
                        .image(joinRequest.getImage())
                        .password(joinRequest.getPassword())
                        .phoneNumber(joinRequest.getPhoneNumber())
                        .build());
        return ApiResult.ok(new UserResponse(user));
    }

    // 회원정보 수정
    @PutMapping("/{userId}")
    public ApiResult<UserResponse> updateUserInfo(@PathVariable Long userId, @Validated @RequestBody UpdateUserInfoRequest updateUserInfoRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        User user = userService.updateUserInfo(userId,
                updateUserInfoRequest.getNickname(),
                updateUserInfoRequest.getImage(),
                updateUserInfoRequest.getPhoneNumber());
        return ApiResult.ok(new UserResponse(user));
    }

    // 회원 탈퇴
    @PatchMapping("/{userId}")
    public ApiResult<Void> quit(@PathVariable Long userId) {
        userService.quit(userId);
        return ApiResult.ok();
    }
}
