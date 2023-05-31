package org.bbaemin.user.user.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class JoinRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String nickname;
    private String image;

    // TODO - validation check : password.equals(passwordConfirm)
    @NotBlank
    private String password;
    private String passwordConfirm;

    @NotBlank
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
