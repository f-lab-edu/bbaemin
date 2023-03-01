package org.bbaemin.api.user.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bbaemin.user.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;
    private String image;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String refreshToken;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted = false;
    private LocalDateTime deletedAt;

    @Builder
    private User(Long userId, String email, String nickname, String image, String password, String phoneNumber, Role role) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.image = image;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
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

    public void setRole(Role role) { this.role = role; }

    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(role.getValue()));
        return roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
