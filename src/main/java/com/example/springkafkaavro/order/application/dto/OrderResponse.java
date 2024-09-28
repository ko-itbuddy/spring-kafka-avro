package com.example.springkafkaavro.order.application.dto;

import com.example.springkafkaavro.order.domain.Order;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주문 응답 객체")
public record OrderResponse(
    @Schema(description = "주문 아이디")
    Long orderId,
    @Schema(description = "주문 상품 아이디")
    Long itemId,
    @Schema(description = "주문 수량")
    Long quantity,
    @Schema(description = "주문 금액")
    Long totalPrice
) {

    public static OrderResponse of(Order order) {
        return new OrderResponse(order.getId(), order.getItemId(), order.getQuantity(), order.getTotalPrice());
    }

}
