package org.bbaemin.user.user.repository;

import org.bbaemin.user.user.vo.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

}
