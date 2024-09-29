package com.example.springkafkaavro.shipping.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "", hidden = true)
public record GetShippingRequest(
    Long orderId
) {

}
