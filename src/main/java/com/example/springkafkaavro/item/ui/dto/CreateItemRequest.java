package com.example.springkafkaavro.item.ui.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Schema(description = "상품 생성 객체")
public record CreateItemRequest(
    @Schema(description = "상품 이름")
    @Length(min = 3, max = 255, message = "상품 이름은 최소 3글자 최대 255글자입니다.")
    @NotNull(message = "상품 이름은 null 이면 안됩니다.")
    String name,

    @Schema(description = "상품 가격")
    @Range(min = 1L, max = 500000000, message = "상품 가격범위는 1원~ 5억원 입니다.")
    @NotNull(message = "상품 가격은 null 이면 안됩니다.")
    Long price,

    @Schema(description = "상품 재고 수량")
    @PositiveOrZero(message = "상품 재고 수량은 0이상의 수입니다.")
    @NotNull(message = "상품 재고 수량은 null 이면 안됩니다.")
    Long stockQuantity
) {

}
