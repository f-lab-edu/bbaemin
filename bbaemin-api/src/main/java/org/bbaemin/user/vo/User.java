package org.bbaemin.user.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private Long userId;
    private String email;
    private String nickname;
    private String image;
    private String password;
    private String phoneNumber;

    private boolean deleted = false;

    @Builder
    private User(Long userId, String email, String nickname, String image, String password, String phoneNumber) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.image = image;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public void quit() {
        this.deleted = true;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
