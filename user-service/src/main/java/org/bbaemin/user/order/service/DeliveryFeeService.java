package org.bbaemin.user.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class DeliveryFeeService {

    private WebClient client = WebClient.create();

    public Mono<Integer> getDeliveryFee(int orderAmount) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        // TODO - property
                        .host("http://localhost:8080")
                        .path("/api/v1/delivery/fee")
                        .queryParam("orderAmount", orderAmount)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Integer.class)
                .log();
    }
}
