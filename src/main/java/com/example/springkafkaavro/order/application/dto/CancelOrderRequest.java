package com.example.springkafkaavro.order.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "주문 취소 객체")
public record CancelOrderRequest(
    @Schema(description = "주문 취소 대상 주문 아이디")
    @NotNull(message = "주문 취소 대상 주문 아이디는 null이면 안됩니다.")
    Long orderId
) {

}
