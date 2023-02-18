package org.bbaemin.item.repository;

import org.bbaemin.item.domain.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    @Query("select i from ItemEntity i join fetch i.itemCategory join fetch i.itemStore")
    List<ItemEntity> findAll();

    @Query("select i from ItemEntity i join fetch i.itemCategory join fetch i.itemStore where i.itemId = :itemId")
    Optional<ItemEntity> findByItemId(@Param("itemId") Long itemId);
}
