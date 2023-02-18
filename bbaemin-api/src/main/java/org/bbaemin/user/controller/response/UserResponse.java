package org.bbaemin.user.controller.response;

import lombok.Getter;
import org.bbaemin.user.vo.User;

@Getter
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
