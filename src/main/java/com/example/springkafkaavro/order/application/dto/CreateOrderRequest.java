package com.example.springkafkaavro.order.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Schema(description = "주문 생성 객체")
public record CreateOrderRequest(
    @Valid
    @Schema(description = "주문 요청 목록")
    @Size(min = 1, message = "주문 요청 목록은 1개이상 존재해야 합니다.")
    @NotNull(message = "주문 요청 목록은 null이면 안됩니다.")
    List<OrderRequest> orders
) {


}
