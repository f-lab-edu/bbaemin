package org.bbaemin.user.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateUserInfoRequest {

    private String nickname;
    private String image;
    private String phoneNumber;

    @Builder
    private UpdateUserInfoRequest(String nickname, String image, String phoneNumber) {
        this.nickname = nickname;
        this.image = image;
        this.phoneNumber = phoneNumber;
    }
}
