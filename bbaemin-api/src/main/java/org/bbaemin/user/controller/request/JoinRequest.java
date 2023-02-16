package org.bbaemin.user.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinRequest {

    private String email;
    private String nickname;
    private String image;

    // TODO - validation check : password.equals(passwordConfirm)
    private String password;
    private String passwordConfirm;

    private String phoneNumber;

    @Builder
    private JoinRequest(String email, String nickname, String image, String password, String passwordConfirm, String phoneNumber) {
        this.email = email;
        this.nickname = nickname;
        this.image = image;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.phoneNumber = phoneNumber;
    }
}
