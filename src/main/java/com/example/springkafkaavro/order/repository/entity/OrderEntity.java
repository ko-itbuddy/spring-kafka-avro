package com.example.springkafkaavro.order.repository.entity;

import com.example.springkafkaavro.common.repository.entity.BaseEntity;
import com.example.springkafkaavro.order.domain.Order;
import com.example.springkafkaavro.order.domain.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`order`")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long itemId;

    private Long quantity;

    private Long totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Order toDomain() {
        return Order.builder()
                    .id(id)
                    .itemId(itemId)
                    .quantity(quantity)
                    .totalPrice(totalPrice)
                    .build();
    }

    public static OrderEntity of(Order order) {
        return OrderEntity.builder()
                          .id(order.getId())
                          .itemId(order.getItemId())
                          .quantity(order.getQuantity())
                          .totalPrice(order.getTotalPrice())
                          .build();
    }

}
