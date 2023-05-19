package org.bbaemin.user.user.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdateUserInfoRequest {

    @NotBlank
    private String nickname;
    private String image;
    @NotBlank
    private String phoneNumber;

    @Builder
    private UpdateUserInfoRequest(String nickname, String image, String phoneNumber) {
        this.nickname = nickname;
        this.image = image;
        this.phoneNumber = phoneNumber;
    }
}
