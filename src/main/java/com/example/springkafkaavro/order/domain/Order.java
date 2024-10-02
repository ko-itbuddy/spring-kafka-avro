package com.example.springkafkaavro.order.domain;

import com.example.springkafkaavro.item.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Order{

    private Long id;
    private Long itemId;
    private Long quantity;
    private Long totalPrice;
    private OrderStatus status;

    public void calculateTotalPrice(Item item) {
        this.totalPrice = item.getPrice() * quantity;
    }

    public void cancelOrder(){
        status = OrderStatus.CANCELED;
    }

}
