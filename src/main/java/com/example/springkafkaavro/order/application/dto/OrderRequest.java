package com.example.springkafkaavro.order.application.dto;

import com.example.springkafkaavro.order.domain.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "주문 요청 객체")
public record OrderRequest(
    @Schema(description = "주문 상품 아이디", example = "1")
    @Min(value = 1, message = "주문 상품 아이디는 1이상의 수입니다.")
    Long itemId,

    @Schema(description = "주문 수량", example = "2")
    @Min(value = 1, message = "주문 수량은 1 이상의 수입니다.")
    Long quantity
) {

    public Order toDomain() {
        return Order.builder()
                    .itemId(itemId)
                    .quantity(quantity)
                    .build();
    }

}
