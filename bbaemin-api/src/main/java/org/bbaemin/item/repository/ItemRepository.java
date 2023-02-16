package org.bbaemin.item.repository;

import org.bbaemin.item.controller.response.ItemImageResponse;
import org.bbaemin.item.controller.response.ItemResponse;
import org.bbaemin.item.vo.Item;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ItemRepository {

    private static final List<ItemResponse> itemList = new ArrayList<>();
    private static Long itemId = 0L;

    static {
        itemList.add(
                ItemResponse.builder()
                        .itemId(++itemId)
                        .category("과일")
                        .store("B마트 인천점")
                        .name("청동사과")
                        .description("맛있고 싱싱한 청동사과")
                        .price(2000)
                        .quantity(999)
                        .itemImageResponse(
                                Arrays.asList(
                                        ItemImageResponse.builder()
                                                .url("https://image.thumbnail.com")
                                                .type("thumb-nail")
                                                .build(),
                                        ItemImageResponse.builder()
                                                .url("https://image.detail.com")
                                                .type("detail")
                                                .build()
                                )
                        )
                        .build());

        itemList.add(
                ItemResponse.builder()
                        .itemId(++itemId)
                        .category("유제품")
                        .store("B마트 인천점")
                        .name("서울우유 저지방우유 1000ml")
                        .description("서울 우유 저지방우유 1000ml")
                        .price(5000)
                        .quantity(999)
                        .itemImageResponse(
                                Arrays.asList(
                                        ItemImageResponse.builder()
                                                .url("https://image.thumbnail.com")
                                                .type("thumbnail")
                                                .build(),
                                        ItemImageResponse.builder()
                                                .url("https://image.detail.com")
                                                .type("detail")
                                                .build()
                                )
                        )
                        .build()
        );
    }

    public ItemResponse oneResponse(Long itemId) {
        return itemList.stream().filter(item -> item.getItemId().equals(itemId))
                .findFirst().orElseThrow();
    }

    /**
     * <pre>
     * 1. MethodName : findAll
     * 2. ClassName  : ItemRepository.java
     * 3. Comment    : 아이템 리스트 조회
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 07.
     * </pre>
     */
    public List<ItemResponse> findAll() {
        return itemList;
    }

    /**
     * <pre>
     * 1. MethodName : findById
     * 2. ClassName  : ItemRepository.java
     * 3. Comment    : 아이템 상세 조회
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 07.
     * </pre>
     */
    public ItemResponse findById(Long itemId) {
        return oneResponse(itemId);
    }

    /**
     * <pre>
     * 1. MethodName : save
     * 2. ClassName  : ItemRepository.java
     * 3. Comment    : 아이템 등록
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 07.
     * </pre>
     */
    public ItemResponse save(Item item) {
        return ItemResponse.builder()
                .itemId(++itemId)
                .category("과자")
                .store("B마트 계양점")
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .itemImageResponse(
                        Arrays.asList(
                                ItemImageResponse.builder()
                                        .url(item.getItemImageRequest().get(0).getUrl())
                                        .type(item.getItemImageRequest().get(0).getType())
                                        .build(),
                                ItemImageResponse.builder()
                                        .url(item.getItemImageRequest().get(1).getUrl())
                                        .type(item.getItemImageRequest().get(1).getType())
                                        .build()
                        )
                )
                .build();
    }

    /**
     * <pre>
     * 1. MethodName : update
     * 2. ClassName  : ItemRepository.java
     * 3. Comment    : 아이템 수정
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 07.
     * </pre>
     */
    public ItemResponse update(Long itemId, Item item) {
        return ItemResponse.builder()
                .itemId(itemId)
                .category("과자")
                .store("B마트 계양점")
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .itemImageResponse(
                        Arrays.asList(
                                ItemImageResponse.builder()
                                        .url(item.getItemImageRequest().get(0).getUrl())
                                        .type(item.getItemImageRequest().get(0).getType())
                                        .build(),
                                ItemImageResponse.builder()
                                        .url(item.getItemImageRequest().get(1).getUrl())
                                        .type(item.getItemImageRequest().get(1).getType())
                                        .build()
                        )
                )
                .build();
    }

    /**
     * <pre>
     * 1. MethodName : deleteById
     * 2. ClassName  : ItemRepository.java
     * 3. Comment    : 아이템 삭제
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 07.
     * </pre>
     */
    public Long deleteById(Long itemId) {
        itemList.stream().filter(item -> item.getItemId().equals(itemId))
                .collect(Collectors.toList()).remove(0);

        return itemId;
    }
}
