package org.bbaemin.user.user.controller;

import org.bbaemin.config.response.ApiResult;
import org.bbaemin.user.user.controller.request.JoinRequest;
import org.bbaemin.user.user.controller.request.UpdateUserInfoRequest;
import org.bbaemin.user.user.repository.UserRepository;
import org.bbaemin.user.user.service.UserService;
import org.bbaemin.user.user.vo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

//@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class UserControllerIntegrationTest {

    private String BASE_URL = "/api/v1/users";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    private User user1;
    private Long user1Id;
    private User user2;
    private Long user2Id;

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll()
                .log()
                .block();

        user1 = User.builder()
                .userId(null)
                .email("user1@email.com")
                .nickname("user1")
                .password("password")
                .image(null)
                .phoneNumber("010-1111-1111")
                .build();
        userRepository.save(user1)
                .log()
                .doOnSuccess(user -> {
                    user1Id = user.getUserId();
                })
                .block();

        user2 = User.builder()
                .userId(null)
                .email("user2@email.com")
                .nickname("user2")
                .password("password")
                .image(null)
                .phoneNumber("010-2222-2222")
                .build();
        userRepository.save(user2)
                .log()
                .doOnSuccess(user -> {
                    user2Id = user.getUserId();
                })
                .block();
    }

    @Test
    void listUser() {
        webTestClient.get()
                .uri(BASE_URL)
                .exchange()
                .expectStatus().isOk()
//                .expectBody(ApiResult.class)
                .expectBody()
                .jsonPath("$.code").isEqualTo(ApiResult.ResultCode.SUCCESS.name())
                .jsonPath("$.result[0].userId").isEqualTo(user1.getUserId())
                .jsonPath("$.result[0].email").isEqualTo(user1.getEmail())
                .jsonPath("$.result[0].nickname").isEqualTo(user1.getNickname())
                .jsonPath("$.result[0].image").isEqualTo(user1.getImage())
                .jsonPath("$.result[0].phoneNumber").isEqualTo(user1.getPhoneNumber())
                .jsonPath("$.result[1].userId").isEqualTo(user2.getUserId())
                .jsonPath("$.result[1].email").isEqualTo(user2.getEmail())
                .jsonPath("$.result[1].nickname").isEqualTo(user2.getNickname())
                .jsonPath("$.result[1].image").isEqualTo(user2.getImage())
                .jsonPath("$.result[1].phoneNumber").isEqualTo(user2.getPhoneNumber());
    }

    @Test
    void getUser() {
        webTestClient.get()
//                .uri(uriBuilder -> {
//                    URI uri = uriBuilder.path(BASE_URL + "/{userId}").build(user1.getUserId());
//                    System.out.println(uri);
//                    return uri;
//                })
                .uri(String.format("%s/%d", BASE_URL, user1.getUserId()))
                .exchange()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ApiResult.ResultCode.SUCCESS.name())
                .jsonPath("$.result.userId").isEqualTo(user1.getUserId())
                .jsonPath("$.result.email").isEqualTo(user1.getEmail())
                .jsonPath("$.result.nickname").isEqualTo(user1.getNickname())
                .jsonPath("$.result.image").isEqualTo(user1.getImage())
                .jsonPath("$.result.phoneNumber").isEqualTo(user1.getPhoneNumber());
    }

    @Test
    void join() {
        JoinRequest joinRequest = JoinRequest.builder()
                .email("user@email.com")
                .nickname("user")
                .image(null)
                .password("password")
                .phoneNumber("010-1234-5678")
                .build();
        webTestClient.post()
                .uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(joinRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ApiResult.ResultCode.CREATED.name())
                .jsonPath("$.result.userId").isNotEmpty()
                .jsonPath("$.result.email").isEqualTo(joinRequest.getEmail())
                .jsonPath("$.result.nickname").isEqualTo(joinRequest.getNickname())
                .jsonPath("$.result.image").isEqualTo(joinRequest.getImage())
                .jsonPath("$.result.phoneNumber").isEqualTo(joinRequest.getPhoneNumber());
    }

    @Test
    void updateUserInfo() {

        UpdateUserInfoRequest updateUserInfoRequest = UpdateUserInfoRequest.builder()
                .nickname("user")
                .image("image")
                .phoneNumber("010-1234-5678")
                .build();
        webTestClient.patch()
                .uri(String.format("%s/%d", BASE_URL, user1.getUserId()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateUserInfoRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ApiResult.ResultCode.SUCCESS.name())
                .jsonPath("$.result.userId").isEqualTo(user1.getUserId())
                .jsonPath("$.result.email").isEqualTo(user1.getEmail())
                .jsonPath("$.result.nickname").isEqualTo(updateUserInfoRequest.getNickname())
                .jsonPath("$.result.image").isEqualTo(updateUserInfoRequest.getImage())
                .jsonPath("$.result.phoneNumber").isEqualTo(updateUserInfoRequest.getPhoneNumber());
    }

//    @Transactional
    @Test
    void quit() {

        webTestClient.patch()
                .uri(String.format("%s/%d/quit", BASE_URL, user1.getUserId()))
                .exchange()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ApiResult.ResultCode.SUCCESS.name());

        User user = userRepository.findById(user1Id).block();
        assert user != null;
        assertThat(user.isDeleted()).isTrue();
        assertThat(user.getDeletedAt()).isNotNull();
    }
}
