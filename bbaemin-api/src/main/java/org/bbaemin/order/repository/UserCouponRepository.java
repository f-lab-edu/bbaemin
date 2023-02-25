package org.bbaemin.order.repository;

import org.bbaemin.order.vo.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
}
