package org.bbaemin.order.controller;

import lombok.RequiredArgsConstructor;
import org.bbaemin.order.controller.request.CreateOrderRequest;
import org.bbaemin.order.controller.response.OrderDetailResponse;
import org.bbaemin.order.controller.response.OrderItemResponse;
import org.bbaemin.order.controller.response.OrderResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
// TODO - CHECK : 버전 구분
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    // 주문 내역 리스트
    @GetMapping
    public List<OrderResponse> listOrder(Long userId) {
        OrderResponse order1 = OrderResponse.builder()
                .orderId(1L)
                .orderDate("2022/11/13(일)")
                .status("주문완료")
                .description("서울우유 저지방우유 1000ml 외 3개")
                .paymentAmount("30,340원")
                .build();
        OrderResponse order2 = OrderResponse.builder()
                .orderId(2L)
                .orderDate("2022/09/28(수)")
                .status("배달완료")
                .description("아이시스 1L 외 2개")
                .paymentAmount("13,880원")
                .build();
        OrderResponse order3 = OrderResponse.builder()
                .orderId(3L)
                .orderDate("2022/05/02(월)")
                .status("주문취소")
                .description("빙그레 바나나맛우유 240ml 외 3개")
                .paymentAmount("8,360원")
                .build();
        return Arrays.asList(order1, order2, order3);
    }

    // 주문 내역 상세보기
    @GetMapping("/{orderId}")
    public OrderDetailResponse getOrderDetail(Long userId, @PathVariable Long orderId) {
        return OrderDetailResponse.builder()
                .status("주문이 완료되었어요")
                .store("B마트 강동천호")
                .description("서울우유 저지방우유 1000ml 외 3개 30,340원")
                .orderDate("2022년 11월 13일 오후 8:20")
                .orderId("40024243")
                .orderItemList(Arrays.asList(
                        OrderItemResponse.builder()
                                .itemName("서울우유 저지방우유 1000ml")
                                .orderPrice("3,290원")
                                .orderCount(1)
                                .totalOrderPrice("3,290원")
                                .itemDescription("서울우유 저지방우유 1000ml 1개 (3,290원)")
                                .build(),
                        OrderItemResponse.builder()
                                .itemName("빙그레 딸기맛우유 240ml 4입")
                                .orderPrice("4,990원")
                                .orderCount(1)
                                .totalOrderPrice("4,990원")
                                .itemDescription("빙그레 딸기맛우유 240ml 4입 1개 (4,990원)")
                                .build(),
                        OrderItemResponse.builder()
                                .itemName("[4개 묶음] 제주삼다수 500ml")
                                .orderPrice("790원")
                                .orderCount(4)
                                .totalOrderPrice("3,160원")
                                .itemDescription("제주삼다수 500ml 4개 (790원)")
                                .build(),
                        OrderItemResponse.builder()
                                .itemName("퍼실 2.7L 용기")
                                .orderPrice("18,900원")
                                .orderCount(1)
                                .totalOrderPrice("18,900원")
                                .itemDescription("퍼실 2.7L 용기 1개 (18,900원)")
                                .build()
                ))
                .orderAmount("30,340원")
                .deliveryFee("0원")
                .paymentAmount("30,340원")
                .paymentMethod("신용/체크카드")
                .deliveryAddress("서울시 강동구")
                .phoneNumber("010-1234-5678")
                .email("user@email.com")
                .messageToRider("감사합니다")
                .build();
    }

    // 주문
    @PostMapping
    public Long order(Long userId, CreateOrderRequest createOrderRequest) {
        return 1L;
    }

    // 주문 내역 삭제
    @DeleteMapping("/{orderId}")
    public Long deleteOrder(Long userId, @PathVariable Long orderId) {
        return orderId;
    }

    // 주문 취소
    @PatchMapping("/{orderId}")
    public Long cancelOrder(Long userId, @PathVariable Long orderId) {
        return orderId;
    }
}
