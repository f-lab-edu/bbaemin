package org.bbaemin.user.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.user.repository.UserRepository;
import org.bbaemin.user.vo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("userId : " + userId));
    }

    @Transactional
    public User join(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User updateUserInfo(Long userId, String nickname, String image, String phoneNumber) {
        // TODO - CHECK : update할 컬럼을 명시적으로 나타내주는 게 좋은가? 변경사항이 많은 경우에는?
        // updateUserInfo(User user) vs updateUserInfo(Long userId, String nickname, String image, String phoneNumber)
        User user = getUser(userId);
        user.setNickname(nickname);
        user.setImage(image);
        user.setPhoneNumber(phoneNumber);
        return user;
    }

    @Transactional
    public void quit(Long userId) {
        User user = getUser(userId);
        user.setDeleted(true);
        user.setDeletedAt(LocalDateTime.now());
    }

}
