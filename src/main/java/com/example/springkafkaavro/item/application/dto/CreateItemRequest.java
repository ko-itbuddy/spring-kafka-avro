package com.example.springkafkaavro.item.application.dto;

import com.example.springkafkaavro.item.domain.Item;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Schema(description = "상품 생성 객체")
public record CreateItemRequest(
    @Schema(description = "상품 이름", example = "좋은 상품")
    @Size(min = 3, max = 255, message = "상품 이름은 최소 3글자 최대 255글자입니다.")
    @NotBlank(message = "상품 이름은 빈문자열이면 안됩니다.")
    String name,

    @Schema(description = "상품 가격", example = "5000")
    @Min(value = 1, message = "상품 가격범위는 1원~ 5억원 입니다.")
    @Max(value = 500000000, message = "상품 가격범위는 1원~ 5억원 입니다.")
    @NotNull(message = "상품 가격은 null 이면 안됩니다.")
    Long price,

    @Schema(description = "상품 재고 수량", example = "10")
    @Min(value = 0, message = "상품 재고 수량은 0이상의 수입니다.")
    @NotNull(message = "상품 재고 수량은 null 이면 안됩니다.")
    Long stockQuantity
) {

    public Item toDomain() {
        return Item.builder()
                   .name(name)
                   .price(price)
                   .stockQuantity(stockQuantity)
                   .build();
    }
}
