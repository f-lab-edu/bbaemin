package org.bbaemin.user.user.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table("user")
public class User {

    @Id
    @Column("user_id")
    private Long userId;

    @Column("email")
    private String email;

    @Column("nickname")
    private String nickname;

    @Column("image")
    private String image;

    @Column("password")
    private String password;

    @Column("phone_number")
    private String phoneNumber;

    @Column("deleted")
    private boolean deleted = false;

    @Column("deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    private User(Long userId, String email, String nickname, String image, String password, String phoneNumber) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.image = image;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
