package org.bbaemin.user.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bbaemin.config.response.ApiResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.bbaemin.enums.ServicePath.DELIVERY_SERVICE;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class DeliveryFeeService {

    @Value("${service.admin}")
    private String admin;

    private WebClient client = WebClient.create();

    public Mono<ApiResult<Integer>> getDeliveryFee(int orderAmount) {
        return client.get()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder.path(admin)
                            .path(DELIVERY_SERVICE.getPath())
                            .path("/fee")
                            .queryParam("orderAmount", orderAmount)
                            .build();
                    log.info("uri : {}", uri);
                    return uri;
                })
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}
