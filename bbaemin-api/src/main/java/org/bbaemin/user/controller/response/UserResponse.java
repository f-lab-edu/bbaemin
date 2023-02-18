package org.bbaemin.user.controller.response;

import lombok.Builder;

public class UserResponse {

    private Long userId;
    private String email;
    private String nickname;
    private String image;

    private String phoneNumber;

    @Builder
    private UserResponse(Long userId, String email, String nickname, String image, String phoneNumber) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.image = image;
        this.phoneNumber = phoneNumber;
    }
}
