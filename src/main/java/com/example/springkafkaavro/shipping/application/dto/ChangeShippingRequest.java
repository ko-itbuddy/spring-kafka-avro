package com.example.springkafkaavro.shipping.application.dto;

import com.example.springkafkaavro.shipping.domain.Shipping;
import com.example.springkafkaavro.shipping.domain.ShippingStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "배송상태 변경 요청 객체")
public record ChangeShippingRequest(

    @Schema(description = "배송 아이디")
    Long orderId,
    @Schema(description = "배송 상태")
    ShippingStatus status

) {

    public Shipping toDomain() {
        return Shipping.builder()
                       .orderId(orderId)
                       .status(status)
                       .build();
    }

}
