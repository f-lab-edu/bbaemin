package org.bbaemin.admin.delivery.repository;

import org.bbaemin.admin.delivery.vo.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @EntityGraph(attributePaths = {"storeCategory"})
    List<Store> findAll();

    @EntityGraph(attributePaths = {"storeCategory"})
    Optional<Store> findById(Long storeId);
}
