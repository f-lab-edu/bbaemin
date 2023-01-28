package org.bbaemin.user.repository;

import org.bbaemin.user.vo.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findById(Long userId);

    User insert(User user);

    User update(User user);

    void updateUserDeleted(Long userId);
}
