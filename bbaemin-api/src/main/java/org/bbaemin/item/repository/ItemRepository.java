package org.bbaemin.item.repository;

import org.bbaemin.item.domain.Item;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @EntityGraph(attributePaths = {"itemStore", "itemCategory"})
    List<Item> findAll();

    @EntityGraph(attributePaths = {"itemStore", "itemCategory"})
    Optional<Item> findByItemId(Long itemId);
}
