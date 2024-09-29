package com.example.springkafkaavro.shipping.repository;

import com.example.springkafkaavro.shipping.application.interfaces.ShippingRepository;
import com.example.springkafkaavro.shipping.domain.Shipping;
import com.example.springkafkaavro.shipping.repository.entity.ShippingEntity;
import com.example.springkafkaavro.shipping.repository.jpa.ShippingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ShippingRepositoryImpl implements ShippingRepository {

    private final ShippingJpaRepository shippingJpaRepository;

    @Override
    public Shipping save(Shipping shipping) {
        return shippingJpaRepository.save(ShippingEntity.of(shipping)).toDomain();
    }

    @Override
    public Shipping findByOrderId(Long orderId) {
        return shippingJpaRepository.findByOrderId(orderId)
                                    .orElseThrow(() -> new IllegalArgumentException(
                                        "해당 주문에 배송정보가 존재하지 않습니다."))
                                    .toDomain();
    }
}
