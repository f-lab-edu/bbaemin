package org.bbaemin.user.controller.request;

import lombok.Builder;
import lombok.Getter;
import org.bbaemin.user.vo.User;

@Getter
public class UpdateUserInfoRequest {

    private String nickname;
    private String image;
    private String phoneNumber;

    public User toEntity(Long userId) {
        return User.builder()
                .userId(userId)
                .nickname(nickname)
                .image(image)
                .phoneNumber(phoneNumber)
                .build();
    }

    @Builder
    private UpdateUserInfoRequest(String nickname, String image, String phoneNumber) {
        this.nickname = nickname;
        this.image = image;
        this.phoneNumber = phoneNumber;
    }
}
