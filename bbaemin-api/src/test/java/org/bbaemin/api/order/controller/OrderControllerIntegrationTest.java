package org.bbaemin.api.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bbaemin.api.order.controller.request.CreateOrderRequest;
import org.bbaemin.api.order.kafka.OrderEventProducer;
import org.bbaemin.api.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.bbaemin.api.order.enums.PaymentMethod.CARD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = {"spring.config.location=classpath:application-test.yml"})
class OrderControllerIntegrationTest {

    private final String BASE_URL = "/api/v1/orders";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    OrderEventProducer orderEventProducer;
    @Autowired
    OrderService orderService;

    private ObjectMapper objectMapper = new ObjectMapper();


//    baseOffset: 3 lastOffset: 3 count: 1 baseSequence: -1 lastSequence: -1 producerId: -1 producerEpoch: -1 partitionLeaderEpoch: 0 isTransactional: false isControl: false position: 1426 CreateTime: 1683489894907 size: 475 magic: 2 compresscodec: none crc: 1360041016 isvalid: true
//            | offset: 3 CreateTime: 1683489894907 keySize: -1 valueSize: 345 sequence: -1 headerKeys: [__TypeId__] payload: {"userId":1,"order":{"orderId":null,"user":null,"orderDate":[2023,5,8,5,4,54,750100900],"status":"COMPLETE_ORDER","orderAmount":0,"deliveryFee":0,"paymentAmount":0,"paymentMethod":"CARD","deliveryAddress":"서울시 강동구","phoneNumber":"010-1234-5678","email":"user@email.com","messageToRider":"감사합니다!"},"discountCouponIdList":[]}
    @Test
    void order() throws Exception {

        // given
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
                .andExpect(status().isOk());

    }
}
