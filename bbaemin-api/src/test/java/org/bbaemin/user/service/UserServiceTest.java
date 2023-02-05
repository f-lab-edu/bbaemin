package org.bbaemin.user.service;

import org.bbaemin.user.controller.request.JoinRequest;
import org.bbaemin.user.controller.request.UpdateUserInfoRequest;
import org.bbaemin.user.controller.response.UserResponse;
import org.bbaemin.user.repository.UserMemoryRepository;
import org.bbaemin.user.repository.UserRepository;
import org.bbaemin.user.vo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserServiceTest {

    UserRepository userRepository = new UserMemoryRepository();
    UserService userService = new UserService(userRepository);

    @BeforeEach
    void init() {
        UserMemoryRepository.clear();
    }
/*
    @Test
    void getUser() {
        // given
        // when
        User user = userService.getUser(1L);
        // then
        assertThat(user.getEmail()).isEqualTo("user1@email.com");
        assertThat(user.getNickname()).isEqualTo("user1");
        assertThat(user.getImage()).isEqualTo(null);
        assertThat(user.getPhoneNumber()).isEqualTo("010-1234-5678");
    }*/

    @Test
    void join() {

        // given
        // when
        JoinRequest joinRequest = JoinRequest.builder()
                .email("user@email.com")
                .nickname("user")
                .image(null)
                .password("password")
                .passwordConfirm("password")
                .phoneNumber("010-2233-4455")
                .build();
        User saved = userService.join(joinRequest.toEntity());

        // then
        User user = userService.getUser(saved.getUserId());
        assertThat(saved).isEqualTo(user);
        System.out.println(user);
        System.out.println(new UserResponse(user));
    }

    @Test
    void updateUserInfo() {

        // given
        JoinRequest joinRequest = JoinRequest.builder()
                .email("user@email.com")
                .nickname("user")
                .image(null)
                .password("password")
                .passwordConfirm("password")
                .phoneNumber("010-2233-4455")
                .build();
        User user = userService.join(joinRequest.toEntity());

        // when
        UpdateUserInfoRequest updateUserInfoRequest = UpdateUserInfoRequest.builder()
                .nickname("new_user")
                .image("image_path")
                .phoneNumber("010-2233-4456")
                .build();
        User updated = userService.updateUserInfo(updateUserInfoRequest.toEntity(user.getUserId()));

        // then
        assertAll(
                () -> assertThat(updated.getUserId()).isEqualTo(user.getUserId()),
                () -> assertThat(updated.getNickname()).isEqualTo(updateUserInfoRequest.getNickname()),
                () -> assertThat(updated.getImage()).isEqualTo(updateUserInfoRequest.getImage()),
                () -> assertThat(updated.getPhoneNumber()).isEqualTo(updateUserInfoRequest.getPhoneNumber())
        );

    }

    @Test
    void quit() {

        // given
        JoinRequest joinRequest = JoinRequest.builder()
                .email("user@email.com")
                .nickname("user")
                .image(null)
                .password("password")
                .passwordConfirm("password")
                .phoneNumber("010-2233-4455")
                .build();
        User user = userService.join(joinRequest.toEntity());

        // when
        userService.quit(user.getUserId());
        // then
        assertThat(user.isDeleted()).isTrue();
    }
}
