package org.bbaemin.user.order.controller;

import org.bbaemin.config.response.ApiResult;
import org.bbaemin.user.order.controller.request.CreateOrderRequest;
import org.bbaemin.user.order.service.OrderService;
import org.bbaemin.user.order.vo.Order;
import org.bbaemin.user.order.vo.OrderItem;
import org.bbaemin.user.user.vo.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.bbaemin.user.order.enums.OrderStatus.CANCEL_ORDER;
import static org.bbaemin.user.order.enums.OrderStatus.COMPLETE_ORDER;
import static org.bbaemin.user.order.enums.PaymentMethod.CARD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient(timeout = "10000")
@WebFluxTest(OrderController.class)
public class OrderTest {

    private String BASE_URL = "/api/v1/orders";

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    OrderService orderService;

    private User user = User.builder()
            .userId(1L)
            .email("user@email.com")
            .nickname("user")
            .image(null)
            .phoneNumber("010-1234-5678")
            .build();

    private Order order = Order.builder()
            .orderId(1L)
            .userId(user.getUserId())
            .orderDate(LocalDateTime.now())
            .status(COMPLETE_ORDER)
            .deliveryAddress("서울시 강동구")
            .phoneNumber("010-1234-5678")
            .email("user@email.com")
            .messageToRider("감사합니다")
            .orderAmount(20000)
            .deliveryFee(5000)
            .paymentAmount(25000)
            .paymentMethod(CARD)
            .build();

    private OrderItem orderItem = OrderItem.builder()
            .orderItemId(1L)
            .orderId(1L)
            .itemId(1L)
            .itemName("item_name")
            .itemDescription("item_desc")
            .orderPrice(10000)
            .orderCount(2)
            .build();

    @Test
    void order() {
        // given
        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder()
                .deliveryAddress("서울시 강동구")
                .phoneNumber("010-1234-5678")
                .email("user@email.com")
                .messageToRider("감사합니다")
                .paymentMethod(CARD)
                .build();
//        Order _order = Order.builder()
//                .orderDate(LocalDateTime.now())
//                .status(COMPLETE_ORDER)
//                .deliveryAddress(createOrderRequest.getDeliveryAddress())
//                .phoneNumber(createOrderRequest.getPhoneNumber())
//                .email(createOrderRequest.getEmail())
//                .messageToRider(createOrderRequest.getMessageToRider())
//                .paymentMethod(createOrderRequest.getPaymentMethod())
//                .build();
        Order order = Order.builder()
                .orderId(1L)
                .userId(user.getUserId())
                .orderDate(LocalDateTime.now())
                .status(COMPLETE_ORDER)
                .deliveryAddress("서울시 강동구")
                .phoneNumber("010-1234-5678")
                .email("user@email.com")
                .messageToRider("감사합니다")
                .orderAmount(20000)
                .deliveryFee(5000)
                .paymentAmount(25000)
                .paymentMethod(CARD)
                .build();
        doReturn(Mono.just(order))
                .when(orderService).order(any(Long.class), any(Order.class), any());
        doReturn(Flux.just(orderItem))
                .when(orderService).getOrderItemListByOrderId(1L);

        // when
        // then
        webTestClient.post()
                .uri(String.format("%s?userId=%d", BASE_URL, 1L))
                .bodyValue(createOrderRequest)
                .exchange()
//                .expectBody(ApiResult.class)
                .expectBody()
                .jsonPath("$.code").isEqualTo(ApiResult.ResultCode.SUCCESS.name())
                .jsonPath("$.result.orderId").isEqualTo(order.getOrderId())
                .jsonPath("$.result.status").isEqualTo(order.getStatus())
                .jsonPath("$.result.paymentAmount").isEqualTo(order.getPaymentAmount())
                .jsonPath("$.result.orderDate").isNotEmpty()
                .jsonPath("$.result.orderAmount").isEqualTo(order.getOrderAmount())
                .jsonPath("$.result.deliveryFee").isEqualTo(order.getDeliveryFee())
                .jsonPath("$.result.paymentMethod").isEqualTo(order.getPaymentMethod())
                .jsonPath("$.result.deliveryAddress").isEqualTo(order.getDeliveryAddress())
                .jsonPath("$.result.phoneNumber").isEqualTo(order.getPhoneNumber())
                .jsonPath("$.result.email").isEqualTo(order.getEmail())
                .jsonPath("$.result.messageToRider").isEqualTo(order.getMessageToRider())
                .jsonPath("$.result.orderDate").isNotEmpty()
                .jsonPath("$.result.orderItemList[0].itemName").isEqualTo(orderItem.getItemName())
                .jsonPath("$.result.orderItemList[0].itemDescription").isEqualTo(orderItem.getItemDescription())
                .jsonPath("$.result.orderItemList[0].orderPrice").isEqualTo(orderItem.getOrderPrice())
                .jsonPath("$.result.orderItemList[0].orderCount").isEqualTo(orderItem.getOrderCount())
                .jsonPath("$.result.orderItemList[0].totalOrderPrice").isEqualTo(orderItem.getOrderPrice() * orderItem.getOrderCount());
    }
}
