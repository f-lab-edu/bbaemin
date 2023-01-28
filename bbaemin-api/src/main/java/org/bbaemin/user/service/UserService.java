package org.bbaemin.user.service;

import lombok.RequiredArgsConstructor;
import org.bbaemin.user.controller.request.JoinRequest;
import org.bbaemin.user.controller.request.UpdateUserInfoRequest;
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

    public User join(JoinRequest joinRequest) {
        User user = joinRequest.toEntity();
        return userRepository.insert(user);
    }

    public User updateUserInfo(Long userId, UpdateUserInfoRequest updateUserInfoRequest) {
        User user = updateUserInfoRequest.toEntity(userId);
        return userRepository.update(user);
    }

    public void quit(Long userId) {
//        User user = userRepository.findById(userId);
//        user.quit();
//        userRepository.update(user);
        userRepository.updateUserDeleted(userId);
    }

}
