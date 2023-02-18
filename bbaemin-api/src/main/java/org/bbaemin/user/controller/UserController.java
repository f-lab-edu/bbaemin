package org.bbaemin.user.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.user.controller.request.JoinRequest;
import org.bbaemin.user.controller.request.UpdateUserInfoRequest;
import org.bbaemin.user.controller.response.UserResponse;
import org.bbaemin.user.service.UserService;
import org.bbaemin.user.vo.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 리스트
    @GetMapping
    public List<UserResponse> listUser() {
        return userService.getUserList().stream()
                .map(UserResponse::new).collect(Collectors.toList());
    }

    // 회원 조회
    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        return new UserResponse(user);
    }

    // 회원 등록
    @PostMapping
    public UserResponse join(@RequestBody JoinRequest joinRequest) {
        User user = userService.join(
                User.builder()
                        .email(joinRequest.getEmail())
                        .nickname(joinRequest.getNickname())
                        .image(joinRequest.getImage())
                        .password(joinRequest.getPassword())
                        .phoneNumber(joinRequest.getPhoneNumber())
                        .build());
        return new UserResponse(user);
    }

    // 회원정보 수정
    @PutMapping("/{userId}")
    public UserResponse updateUserInfo(@PathVariable Long userId, @RequestBody UpdateUserInfoRequest updateUserInfoRequest) {
        User user = userService.updateUserInfo(userId,
                updateUserInfoRequest.getNickname(),
                updateUserInfoRequest.getImage(),
                updateUserInfoRequest.getPhoneNumber());
        return new UserResponse(user);
    }

    // 회원 탈퇴
    @PatchMapping("/{userId}")
    public void quit(@PathVariable Long userId) {
        userService.quit(userId);
    }
}
