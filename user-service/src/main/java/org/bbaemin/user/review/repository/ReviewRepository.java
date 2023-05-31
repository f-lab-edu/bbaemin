package org.bbaemin.user.review.repository;

import org.bbaemin.user.review.vo.Review;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface ReviewRepository extends ReactiveCrudRepository<Review, Long> {
}
