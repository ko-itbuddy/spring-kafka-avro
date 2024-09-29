package com.example.springkafkaavro.shipping.application.dto;

import com.example.springkafkaavro.shipping.domain.Shipping;
import com.example.springkafkaavro.shipping.domain.ShippingStatus;
import com.example.springkafkaavro.shipping.repository.entity.ShippingEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "배송 상태 응답 객체")
public record ShippingResponse(
    @Schema(description = "배송 아이디")
    Long shippingId,
    @Schema(description = "배송 주문 아이디")
    Long orderId,
    @Schema(description = "배송 상태")
    ShippingStatus status
) {
    public static ShippingResponse of(Shipping shipping){
        return new ShippingResponse(shipping.getId(), shipping.getOrderId(), shipping.getStatus());
    }

}
