package org.bbaemin.review.repository;

import org.bbaemin.review.vo.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
