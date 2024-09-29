package com.example.springkafkaavro.shipping.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Shipping {
    private Long id;
    private Long orderId;
    private ShippingStatus status;
}
