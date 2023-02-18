package org.bbaemin.user.controller.response;

import org.bbaemin.user.vo.User;

public class UserResponse {

    private Long userId;
    private String email;
    private String nickname;
    private String image;

    private String phoneNumber;

    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.image = user.getImage();
        this.phoneNumber = user.getPhoneNumber();
    }
}
