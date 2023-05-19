package org.bbaemin.user.user.service;

import org.bbaemin.user.user.repository.UserRepository;
import org.bbaemin.user.user.vo.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;

    private User user1 = User.builder()
            .userId(1L)
            .email("user1@email.com")
            .nickname("user1")
            .password("password")
            .image(null)
            .phoneNumber("010-1111-1111")
            .build();

    private User user2 = User.builder()
            .userId(2L)
                .email("user2@email.com")
                .nickname("user2")
                .password("password")
                .image(null)
                .phoneNumber("010-2222-2222")
                .build();

    @Test
    void getUserList() {
        doReturn(Flux.just(user1, user2))
                .when(userRepository).findAll();
        Flux<User> userFlux = userService.getUserList();
        StepVerifier.create(userFlux.log())
//                .expectNextCount(2)
                .expectNextMatches(user -> user.getEmail().equals("user1@email.com"))
                .expectNextMatches(user -> user.getEmail().equals("user2@email.com"))
                .expectComplete()
                .verify();
    }

    @Test
    void getUser_when_not_exist() {
        doReturn(Mono.empty())
                .when(userRepository).findById(1L);
        Mono<User> user = userService.getUser(1L);
        StepVerifier.create(user)
                .expectErrorMatches(e -> e instanceof NoSuchElementException
                        && e.getMessage().equals("userId : 1"))
                .verify();
    }

    @Test
    void createUser() {
        doReturn(Mono.just(user1))
                .when(userRepository).save(any(User.class));
        Mono<User> user = userService.createUser(user1);
        StepVerifier.create(user)
                .expectNextCount(1)
                .expectComplete()
                .verify();
        verify(userRepository).save(user1);
    }

    @Test
    void updateUserInfo() {
        // given
        doReturn(Mono.just(user1))
                .when(userRepository).findById(1L);
        doReturn(Mono.just(user1))
                .when(userRepository).save(any(User.class));


        Mono<User> user = userService.updateUserInfo(1L,
                "updated_nickname",
                "updated_image",
                "010-0000-0000");
        StepVerifier.create(user)
                .expectNextMatches(updated ->
                        updated.getNickname().equals("updated_nickname") &&
                        updated.getImage().equals("updated_image") &&
                        updated.getPhoneNumber().equals("010-0000-0000"))
                .expectComplete()
                .verify();
    }

    @Test
    void updateUserInfo_when_not_exist() {
        doReturn(Mono.empty())
                .when(userRepository).findById(1L);
        Mono<User> user = userService.updateUserInfo(1L,
                "updated_nickname",
                "updated_image",
                "010-0000-0000");
        StepVerifier.create(user)
                .expectErrorMatches(e -> e instanceof NoSuchElementException
                        && e.getMessage().equals("userId : 1"))
                .verify();
    }

    @Test
    void quit() {
        // given
        doReturn(Mono.just(user1))
                .when(userRepository).findById(1L);
        doReturn(Mono.just(user1))
                .when(userRepository).save(any(User.class));

        // when
        // then
        Mono<User> user = userService.quit(1L);
        StepVerifier.create(user)
                .expectNextMatches(deleted ->
                        deleted.isDeleted() && deleted.getDeletedAt() != null)
                .expectComplete()
                .verify();
    }

}
