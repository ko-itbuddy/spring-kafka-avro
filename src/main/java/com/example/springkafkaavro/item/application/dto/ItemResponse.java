package com.example.springkafkaavro.item.application.dto;

import com.example.springkafkaavro.item.domain.Item;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description="상품 응답 객체")
public record ItemResponse(
    @Schema(description = "상품 아이디")
    Long id,
    @Schema(description = "상품 이름")
    String name,
    @Schema(description = "상품 가격")
    Long price,
    @Schema(description = "상품 재고 수량")
    Long stockQuantity
) {
    public static ItemResponse of(Item item) {
        return new ItemResponse(item.getId(), item.getName(), item.getPrice(),
            item.getStockQuantity());
    }
}
