package org.bbaemin.user.user.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.user.user.repository.UserRepository;
import org.bbaemin.user.user.vo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Flux<User> getUserList() {
        return userRepository.findAll();
    }

    public Mono<User> getUser(Long userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new NoSuchElementException("userId : " + userId)));
    }

    @Transactional
    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public Mono<User> updateUserInfo(Long userId, String nickname, String image, String phoneNumber) {
        return getUser(userId)
                .map(user -> {
                    user.setNickname(nickname);
                    user.setImage(image);
                    user.setPhoneNumber(phoneNumber);
                    return user;
                })
                .flatMap(userRepository::save);
    }

    @Transactional
    public Mono<User> quit(Long userId) {
        return getUser(userId)
                .map(user -> {
                    user.setDeleted(true);
                    user.setDeletedAt(LocalDateTime.now());
                    return user;
                })
                .flatMap(userRepository::save);
    }
}
