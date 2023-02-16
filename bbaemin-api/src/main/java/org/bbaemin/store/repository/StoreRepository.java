package org.bbaemin.store.repository;

import org.bbaemin.store.domain.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    @Query("select s from StoreEntity s join fetch s.storeCategory")
    List<StoreEntity> findAll();

    @Query("select s from StoreEntity s join fetch s.storeCategory where s.storeId = :storeId and s.useYn = true")
    Optional<StoreEntity> findByStoreId(@Param("storeId") Long storeId);
}
