package org.bbaemin.user.order.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(controllers = OrderControllerTest.class,
        properties = {"spring.config.location=classpath:application-test.yml"})
class _OrderControllerTest {

    private final String BASE_URL = "/api/v1/orders";
/*
    @Autowired
    MockMvc mockMvc;
    @MockBean
    OrderService orderService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listOrder() throws Exception {
        // given
        User user = mock(User.class);
        Order order = Order.builder()
                .orderId(1L)
                .user(user)
                .orderDate(LocalDateTime.of(2023, 2, 23, 10, 20, 35))
                .status(COMPLETE_ORDER)
                .orderAmount(20000)
                .deliveryFee(3000)
                .paymentAmount(20000)
                .paymentMethod(CARD)
                .deliveryAddress("서울시 강동구")
                .phoneNumber("010-1234-5678")
                .email("user@email.com")
                .messageToRider("감사합니다!")
                .build();
        doReturn(List.of(order))
                .when(orderService).getOrderListByUserId(1L);
        // when
        mockMvc.perform(get(BASE_URL).param("userId", String.valueOf(1L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result..orderId").value(1))
                .andExpect(jsonPath("$.result..status").value(COMPLETE_ORDER.getName()))
                .andExpect(jsonPath("$.result..paymentAmount").value(20000))
                .andExpect(jsonPath("$.result..orderDate").value("2023-02-23T10:20:35"));
        // then
        verify(orderService).getOrderListByUserId(1L);
    }

    @Test
    void getOrder() throws Exception {
        // given
        User user = mock(User.class);
        Order order = Order.builder()
                .orderId(1L)
                .user(user)
                .orderDate(LocalDateTime.of(2023, 2, 23, 10, 20, 35))
                .status(COMPLETE_ORDER)
                .orderAmount(20000)
                .deliveryFee(3000)
                .paymentAmount(20000)
                .paymentMethod(CARD)
                .deliveryAddress("서울시 강동구")
                .phoneNumber("010-1234-5678")
                .email("user@email.com")
                .messageToRider("감사합니다!")
                .build();
        doReturn(order)
                .when(orderService).getOrder(1L, 1L);

        Item item = mock(Item.class);
        OrderItem orderItem = OrderItem.builder()
                .orderItemId(1L)
                .order(order)
                .item(item)
                .itemName("item_name")
                .itemDescription("item_desc")
                .orderPrice(10000)
                .orderCount(2)
                .build();
        doReturn(List.of(orderItem))
                .when(orderService).getOrderItemListByOrder(order);
        // when
        mockMvc.perform(get(BASE_URL + "/{orderId}", 1L)
                        .param("userId", String.valueOf(1L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.orderId").value(1))
                .andExpect(jsonPath("$.result.status").value(COMPLETE_ORDER.getName()))
                .andExpect(jsonPath("$.result.paymentAmount").value(20000))
                .andExpect(jsonPath("$.result.orderDate").value("2023-02-23T10:20:35"))
                .andExpect(jsonPath("$.result.orderItemList").value(hasSize(1)))
                .andExpect(jsonPath("$.result.orderAmount").value(20000))
                .andExpect(jsonPath("$.result.deliveryFee").value(3000))
                .andExpect(jsonPath("$.result.paymentMethod").value(CARD.getName()))
                .andExpect(jsonPath("$.result.deliveryAddress").value("서울시 강동구"))
                .andExpect(jsonPath("$.result.phoneNumber").value("010-1234-5678"))
                .andExpect(jsonPath("$.result.email").value("user@email.com"))
                .andExpect(jsonPath("$.result.messageToRider").value("감사합니다!"));
        // then
        verify(orderService).getOrder(1L, 1L);
        verify(orderService).getOrderItemListByOrder(order);
    }

    @Test
    void order() throws Exception {
        // given
        User user = mock(User.class);
        Order order = Order.builder()
                .orderId(1L)
                .user(user)
                .orderDate(LocalDateTime.of(2023, 2, 23, 10, 20, 35))
                .status(COMPLETE_ORDER)
                .orderAmount(20000)
                .deliveryFee(3000)
                .paymentAmount(20000)
                .paymentMethod(CARD)
                .deliveryAddress("서울시 강동구")
                .phoneNumber("010-1234-5678")
                .email("user@email.com")
                .messageToRider("감사합니다!")
                .build();
        doReturn(order)
                .when(orderService).order(anyLong(), ArgumentMatchers.any(Order.class), anyList());

        Item item = mock(Item.class);
        OrderItem orderItem = OrderItem.builder()
                .orderItemId(1L)
                .order(order)
                .item(item)
                .itemName("item_name")
                .itemDescription("item_desc")
                .orderPrice(10000)
                .orderCount(2)
                .build();
        doReturn(List.of(orderItem))
                .when(orderService).getOrderItemListByOrder(order);
        // when
        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder()
                .deliveryAddress("서울시 강동구")
                .phoneNumber("010-1234-5678")
                .email("user@email.com")
                .messageToRider("감사합니다!")
                .discountCouponIdList(Collections.EMPTY_LIST)
                .paymentMethod(CARD)
                .build();
        mockMvc.perform(post(BASE_URL)
                        .param("userId", String.valueOf(1L))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CREATED"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.orderId").value(1))
                .andExpect(jsonPath("$.result.status").value(COMPLETE_ORDER.getName()))
                .andExpect(jsonPath("$.result.paymentAmount").value(20000))
                .andExpect(jsonPath("$.result.orderDate").value("2023-02-23T10:20:35"))
                .andExpect(jsonPath("$.result.orderItemList").value(hasSize(1)))
                .andExpect(jsonPath("$.result.orderAmount").value(20000))
                .andExpect(jsonPath("$.result.deliveryFee").value(3000))
                .andExpect(jsonPath("$.result.paymentMethod").value(CARD.getName()))
                .andExpect(jsonPath("$.result.deliveryAddress").value("서울시 강동구"))
                .andExpect(jsonPath("$.result.phoneNumber").value("010-1234-5678"))
                .andExpect(jsonPath("$.result.email").value("user@email.com"))
                .andExpect(jsonPath("$.result.messageToRider").value("감사합니다!"));
        // then
        verify(orderService).order(anyLong(), ArgumentMatchers.any(Order.class), anyList());
        verify(orderService).getOrderItemListByOrder(order);
    }

    @Test
    void deleteOrder() throws Exception {
        // given
        doNothing()
                .when(orderService).deleteOrder(1L, 1L);
        // when
        // then
        mockMvc.perform(delete(BASE_URL + "/{orderId}", 1L)
                        .param("userId", String.valueOf(1L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").doesNotExist());
    }

    @Test
    void cancelOrder() throws Exception {
        // given
        User user = mock(User.class);
        Order order = Order.builder()
                .orderId(1L)
                .user(user)
                .orderDate(LocalDateTime.of(2023, 2, 23, 10, 20, 35))
                .status(OrderStatus.CANCEL_ORDER)
                .orderAmount(20000)
                .deliveryFee(3000)
                .paymentAmount(20000)
                .paymentMethod(CARD)
                .deliveryAddress("서울시 강동구")
                .phoneNumber("010-1234-5678")
                .email("user@email.com")
                .messageToRider("감사합니다!")
                .build();
        doReturn(order)
                .when(orderService).cancelOrder(1L, 1L);

        Item item = mock(Item.class);
        OrderItem orderItem = OrderItem.builder()
                .orderItemId(1L)
                .order(order)
                .item(item)
                .itemName("item_name")
                .itemDescription("item_desc")
                .orderPrice(10000)
                .orderCount(2)
                .build();
        doReturn(List.of(orderItem))
                .when(orderService).getOrderItemListByOrder(order);
        // when
        mockMvc.perform(patch(BASE_URL + "/{orderId}", 1L)
                        .param("userId", String.valueOf(1L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.result").exists())
                .andExpect(jsonPath("$.result.orderId").value(1))
                .andExpect(jsonPath("$.result.status").value(CANCEL_ORDER.getName()))
                .andExpect(jsonPath("$.result.paymentAmount").value(20000))
                .andExpect(jsonPath("$.result.orderDate").value("2023-02-23T10:20:35"))
                .andExpect(jsonPath("$.result.orderItemList").value(hasSize(1)))
                .andExpect(jsonPath("$.result.orderAmount").value(20000))
                .andExpect(jsonPath("$.result.deliveryFee").value(3000))
                .andExpect(jsonPath("$.result.paymentMethod").value(CARD.getName()))
                .andExpect(jsonPath("$.result.deliveryAddress").value("서울시 강동구"))
                .andExpect(jsonPath("$.result.phoneNumber").value("010-1234-5678"))
                .andExpect(jsonPath("$.result.email").value("user@email.com"))
                .andExpect(jsonPath("$.result.messageToRider").value("감사합니다!"));
        // then
        verify(orderService).cancelOrder(1L, 1L);
        verify(orderService).getOrderItemListByOrder(order);
    }*/
}
