package org.bbaemin.user.user.controller;

import org.bbaemin.config.response.ApiResult;
import org.bbaemin.user.user.controller.request.JoinRequest;
import org.bbaemin.user.user.controller.request.UpdateUserInfoRequest;
import org.bbaemin.user.user.service.UserService;
import org.bbaemin.user.user.vo.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient(timeout = "10000")
@WebFluxTest(UserController.class)
public class UserControllerTest {

    private String BASE_URL = "/api/v1/users";

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    UserService userService;

    private User user1 = User.builder()
            .userId(1L)
            .email("user1@email.com")
            .nickname("user1")
            .image(null)
            .phoneNumber("010-1111-1111")
            .build();
    private User user2 = User.builder()
            .userId(2L)
            .email("user2@email.com")
            .nickname("user2")
            .image(null)
            .phoneNumber("010-2222-2222")
            .build();
    private List<User> userList = Arrays.asList(user1, user2);

    @Test
    void listUser() {
        Flux<User> userFlux = Flux.fromIterable(userList);
        // TODO - CHECK : TIMEOUT
/*        Flux<User> userFlux = Flux.create(e -> {
            e.next(User.builder()
                    .userId(1L)
                    .email("user1@email.com")
                    .nickname("user1")
                    .image(null)
                    .phoneNumber("010-1111-1111")
                    .build());
            e.next(User.builder()
                    .userId(2L)
                    .email("user2@email.com")
                    .nickname("user2")
                    .image(null)
                    .phoneNumber("010-2222-2222")
                    .build());
        });*/
        doReturn(userFlux)
                .when(userService).getUserList();
        webTestClient.get()
                .uri(BASE_URL)
                .exchange()
                .expectStatus().isOk()
//                .expectBody(ApiResult.class)
                .expectBody()
                .jsonPath("$.code").isEqualTo(ApiResult.ResultCode.SUCCESS.name())
                .jsonPath("$.result[0].userId").isEqualTo(1)
                .jsonPath("$.result[0].email").isEqualTo("user1@email.com")
                .jsonPath("$.result[0].nickname").isEqualTo("user1")
                .jsonPath("$.result[0].image").isEqualTo(null)
                .jsonPath("$.result[0].phoneNumber").isEqualTo("010-1111-1111")
                .jsonPath("$.result[1].userId").isEqualTo(2)
                .jsonPath("$.result[1].email").isEqualTo("user2@email.com")
                .jsonPath("$.result[1].nickname").isEqualTo("user2")
                .jsonPath("$.result[1].image").isEqualTo(null)
                .jsonPath("$.result[1].phoneNumber").isEqualTo("010-2222-2222");
    }

    @Test
    void getUser() {
        doReturn(Mono.just(user1))
                .when(userService).getUser(1L);
        webTestClient.get()
                .uri(BASE_URL + "/{userId}", 1L)
                .exchange()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ApiResult.ResultCode.SUCCESS.name())
                .jsonPath("$.result.userId").isEqualTo(1)
                .jsonPath("$.result.email").isEqualTo("user1@email.com")
                .jsonPath("$.result.nickname").isEqualTo("user1")
                .jsonPath("$.result.image").isEqualTo(null)
                .jsonPath("$.result.phoneNumber").isEqualTo("010-1111-1111");
    }

    @Test
    void join() {
        JoinRequest joinRequest = JoinRequest.builder()
                .email("user1@email.com")
                .nickname("user1")
                .image(null)
                .password("password")
                .phoneNumber("010-1111-1111")
                .build();
        User user = User.builder()
                .userId(1L)
                .email(joinRequest.getEmail())
                .nickname(joinRequest.getNickname())
                .image(joinRequest.getImage())
                .password(joinRequest.getPassword())
                .phoneNumber(joinRequest.getPhoneNumber())
                .build();
        doReturn(Mono.just(user))
                .when(userService).createUser(any(User.class));

        webTestClient.post()
                .uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(joinRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ApiResult.ResultCode.CREATED.name())
                .jsonPath("$.result.userId").isEqualTo(1)
                .jsonPath("$.result.email").isEqualTo("user1@email.com")
                .jsonPath("$.result.nickname").isEqualTo("user1")
                .jsonPath("$.result.image").isEqualTo(null)
                .jsonPath("$.result.phoneNumber").isEqualTo("010-1111-1111");
    }

    @Test
    void updateUserInfo() {

        UpdateUserInfoRequest updateUserInfoRequest = UpdateUserInfoRequest.builder()
                .nickname("user")
                .image("image")
                .phoneNumber("010-1234-5678")
                .build();
        User user = User.builder()
                .userId(1L)
                .email("user@email.com")
                .nickname("user")
                .image("image")
                .phoneNumber("010-1234-5678")
                .build();
        doReturn(Mono.just(user))
                .when(userService).updateUserInfo(1L, "user", "image", "010-1234-5678");

        webTestClient.patch()
                .uri(BASE_URL + "/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateUserInfoRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ApiResult.ResultCode.SUCCESS.name())
                .jsonPath("$.result.userId").isEqualTo(1)
                .jsonPath("$.result.email").isEqualTo("user@email.com")
                .jsonPath("$.result.nickname").isEqualTo("user")
                .jsonPath("$.result.image").isEqualTo("image")
                .jsonPath("$.result.phoneNumber").isEqualTo("010-1234-5678");
    }

    @Test
    void quit() {

        user1.setDeleted(true);
        user1.setDeletedAt(LocalDateTime.now());
        doReturn(Mono.just(user1))
                .when(userService).quit(1L);

        webTestClient.patch()
                .uri(BASE_URL + "/{userId}/quit", 1L)
                .exchange()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ApiResult.ResultCode.SUCCESS.name())
                .jsonPath("$.result.userId").isEqualTo(1)
                .jsonPath("$.result.email").isEqualTo("user1@email.com")
                .jsonPath("$.result.nickname").isEqualTo("user1")
                .jsonPath("$.result.image").isEqualTo(null)
                .jsonPath("$.result.phoneNumber").isEqualTo("010-1111-1111");
    }
}
