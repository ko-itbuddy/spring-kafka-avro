package com.example.springkafkaavro.item.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.coyote.BadRequestException;

@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Item {

    private Long id;
    private String name;
    private Long price;
    Long stockQuantity;

    public void increaseStockQuantity(Long quantity) {
        this.stockQuantity += quantity;
    }

    public void decreaseStockQuantity(Long quantity) throws BadRequestException {
        if (this.stockQuantity - quantity < 0) {
            throw new BadRequestException("상품 재고 수량은 음수가 될 수 없습니다.");
        }
        this.stockQuantity -= quantity;
    }

}
