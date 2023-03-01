package org.bbaemin.api.user.repository;

import org.bbaemin.api.user.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String userEmail);
}
