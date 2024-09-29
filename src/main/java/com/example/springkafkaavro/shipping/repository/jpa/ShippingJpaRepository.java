package com.example.springkafkaavro.shipping.repository.jpa;

import com.example.springkafkaavro.shipping.repository.entity.ShippingEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingJpaRepository extends JpaRepository<ShippingEntity, Long> {

    Optional<ShippingEntity> findByOrderId(Long orderId);
}
