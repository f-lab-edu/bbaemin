package org.bbaemin.item.repository;

import org.bbaemin.item.controller.request.CreateItemRequest;
import org.bbaemin.item.controller.request.UpdateItemRequest;
import org.bbaemin.item.controller.response.ItemImageResponse;
import org.bbaemin.item.controller.response.ItemResponse;
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
     * 1. MethodName : listItem
     * 2. ClassName  : ItemRepository.java
     * 3. Comment    : 아이템 리스트 조회
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 07.
     * </pre>
     */
    public List<ItemResponse> listItem() {
        return itemList;
    }

    /**
     * <pre>
     * 1. MethodName : getItem
     * 2. ClassName  : ItemRepository.java
     * 3. Comment    : 아이템 상세 조회
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 07.
     * </pre>
     */
    public ItemResponse getItem(Long itemId) {
        return oneResponse(itemId);
    }

    /**
     * <pre>
     * 1. MethodName : createItem
     * 2. ClassName  : ItemRepository.java
     * 3. Comment    : 아이템 등록
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 07.
     * </pre>
     */
    public ItemResponse createItem(CreateItemRequest createItemRequest) {
        return ItemResponse.builder()
                .itemId(++itemId)
                .category("과자")
                .store("B마트 계양점")
                .name("달달한 초코칩")
                .description("순도 100% 초콜릿 초코칩")
                .price(3000)
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
                .build();
    }

    /**
     * <pre>
     * 1. MethodName : updateItem
     * 2. ClassName  : ItemRepository.java
     * 3. Comment    : 아이템 수정
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 07.
     * </pre>
     */
    public ItemResponse updateItem(Long itemId, UpdateItemRequest updateItemRequest) {
        return ItemResponse.builder()
                .itemId(itemId)
                .category("과자")
                .store("B마트 계양점")
                .name(updateItemRequest.getName())
                .description(updateItemRequest.getDescription())
                .price(updateItemRequest.getPrice())
                .quantity(updateItemRequest.getQuantity())
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
                .build();
    }

    /**
     * <pre>
     * 1. MethodName : deleteItem
     * 2. ClassName  : ItemRepository.java
     * 3. Comment    : 아이템 삭제
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 02. 07.
     * </pre>
     */
    public Long deleteItem(Long itemId) {
        itemList.stream().filter(item -> item.getItemId().equals(itemId))
                .collect(Collectors.toList()).remove(0);

        return itemId;
    }
}
