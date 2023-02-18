package org.bbaemin.user.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.user.controller.request.JoinRequest;
import org.bbaemin.user.controller.request.UpdateUserInfoRequest;
import org.bbaemin.user.controller.response.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    // 회원 리스트
    @GetMapping
    public List<UserResponse> listUser() {
        return Arrays.asList(
                UserResponse.builder()
                        .userId(1L)
                        .email("user1@email.com")
                        .nickname("user1")
                        .image(null)
                        .phoneNumber("010-1234-5678")
                        .build(),
                UserResponse.builder()
                        .userId(2L)
                        .email("user2@email.com")
                        .nickname("user2")
                        .image(null)
                        .phoneNumber("010-1111-2222")
                        .build()
        );
    }

    // 회원 조회
    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable Long userId) {
        return UserResponse.builder()
                .userId(userId)
                .email("user1@email.com")
                .nickname("user1")
                .image(null)
                .phoneNumber("010-1234-5678")
                .build();
    }

    // 회원 등록
    @PostMapping
    public Long join(JoinRequest joinRequest) {
        return 1L;
    }

    // 회원정보 수정
    @PutMapping("/{userId}")
    public Long updateUserInfo(@PathVariable Long userId, UpdateUserInfoRequest updateUserInfoRequest) {
        return userId;
    }

    // 회원 탈퇴
    @PatchMapping("/{userId}")
    public Long quit(@PathVariable Long userId) {
        return userId;
    }
}
