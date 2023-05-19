package org.bbaemin.user.user.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.user.user.controller.request.JoinRequest;
import org.bbaemin.user.user.controller.request.UpdateUserInfoRequest;
import org.bbaemin.user.user.controller.response.UserResponse;
import org.bbaemin.user.user.service.UserService;
import org.bbaemin.user.user.vo.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 리스트
    @GetMapping
    public Mono<ApiResult<List<UserResponse>>> listUser() {
        return userService.getUserList()
//                .log()
                .map(user -> UserResponse.builder()
                        .userId(user.getUserId())
                        .email(user.getEmail())
                        .nickname(user.getNickname())
                        .image(user.getImage())
                        .phoneNumber(user.getPhoneNumber())
                        .build())
//                .log()
                .collectList()
//                .log()
                .map(ApiResult::ok)
                .log();
    }
/*
    @GetMapping("/flux")
    public Flux<ApiResult<UserResponse>> fluxUser() {
        return userService.getUserList()
                .map(user -> UserResponse.builder()
                        .userId(user.getUserId())
                        .email(user.getEmail())
                        .nickname(user.getNickname())
                        .image(user.getImage())
                        .phoneNumber(user.getPhoneNumber())
                        .build())
                .log()
                .map(ApiResult::ok)
                .log();
    }*/

    // 회원 조회
    @GetMapping("/{userId}")
    public Mono<ApiResult<UserResponse>> getUser(@PathVariable Long userId) {
        return userService.getUser(userId)
                .map(user -> UserResponse.builder()
                        .userId(user.getUserId())
                        .email(user.getEmail())
                        .nickname(user.getNickname())
                        .image(user.getImage())
                        .phoneNumber(user.getPhoneNumber())
                        .build())
                .map(ApiResult::ok)
                .log();
    }

    // 회원 등록
    @PostMapping
    public Mono<ApiResult<UserResponse>> join(@Validated @RequestBody JoinRequest joinRequest) {
        return userService.createUser(
                User.builder()
                        .email(joinRequest.getEmail())
                        .nickname(joinRequest.getNickname())
                        .image(joinRequest.getImage())
                        .password(joinRequest.getPassword())
                        .phoneNumber(joinRequest.getPhoneNumber())
                        .build())
                .map(user -> UserResponse.builder()
                        .userId(user.getUserId())
                        .email(user.getEmail())
                        .nickname(user.getNickname())
                        .image(user.getImage())
                        .phoneNumber(user.getPhoneNumber())
                        .build())
                .map(ApiResult::created)
                .log();
    }

    // 회원정보 수정
    @PatchMapping("/{userId}")
    public Mono<ApiResult<UserResponse>> updateUserInfo(@PathVariable Long userId, @Validated @RequestBody UpdateUserInfoRequest updateUserInfoRequest) {
        return userService.updateUserInfo(userId, updateUserInfoRequest.getNickname(), updateUserInfoRequest.getImage(), updateUserInfoRequest.getPhoneNumber())
                .map(user -> UserResponse.builder()
                        .userId(user.getUserId())
                        .email(user.getEmail())
                        .nickname(user.getNickname())
                        .image(user.getImage())
                        .phoneNumber(user.getPhoneNumber())
                        .build())
                .map(ApiResult::ok)
                .log();
    }

    // 회원 탈퇴
    @PatchMapping("/{userId}/quit")
    public Mono<ApiResult<UserResponse>> quit(@PathVariable Long userId) {
        return userService.quit(userId)
                .map(user -> UserResponse.builder()
                        .userId(user.getUserId())
                        .email(user.getEmail())
                        .nickname(user.getNickname())
                        .image(user.getImage())
                        .phoneNumber(user.getPhoneNumber())
                        .build())
                .map(ApiResult::ok)
                .log();
    }
}
