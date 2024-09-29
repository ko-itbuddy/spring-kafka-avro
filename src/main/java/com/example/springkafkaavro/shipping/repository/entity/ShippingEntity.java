package com.example.springkafkaavro.shipping.repository.entity;

import com.example.springkafkaavro.common.repository.entity.BaseEntity;
import com.example.springkafkaavro.order.domain.Order;
import com.example.springkafkaavro.order.domain.OrderStatus;
import com.example.springkafkaavro.shipping.domain.Shipping;
import com.example.springkafkaavro.shipping.domain.ShippingStatus;
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
@Table(name = "`shipping`")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShippingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    @Enumerated(EnumType.STRING)
    private ShippingStatus status;

    public Shipping toDomain() {
        return Shipping.builder()
                    .id(id)
                    .orderId(orderId)
                    .status(status)
                    .build();
    }

    public static ShippingEntity of(Shipping shipping) {
        return ShippingEntity.builder()
                             .id(shipping.getId())
                             .orderId(shipping.getOrderId())
                             .status(shipping.getStatus())
                             .build();
    }

}
