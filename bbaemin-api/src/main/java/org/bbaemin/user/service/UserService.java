package org.bbaemin.user.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.user.repository.UserRepository;
import org.bbaemin.user.vo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId);
    }

    public User join(User user) {
        return userRepository.insert(user);
    }

    public User updateUserInfo(User user) {
        return userRepository.update(user);
    }

    public void quit(Long userId) {
//        User user = userRepository.findById(userId);
//        user.quit();
//        userRepository.update(user);
        userRepository.updateUserDeleted(userId);
    }

}
