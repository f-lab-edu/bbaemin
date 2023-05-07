package org.bbaemin.api.order.kafka.message;

import lombok.Builder;
import lombok.Data;

@Data
public class OrderCompletedMessage {

    private String txId;
    private Long orderId;
    private String completedAt;
    private String version;

    @Builder
    private OrderCompletedMessage(String txId, Long orderId, String completedAt, String version) {
        this.txId = txId;
        this.orderId = orderId;
        this.completedAt = completedAt;
        this.version = version;
    }
}
