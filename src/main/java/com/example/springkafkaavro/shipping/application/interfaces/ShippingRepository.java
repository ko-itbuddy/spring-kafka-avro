package com.example.springkafkaavro.shipping.application.interfaces;

import com.example.springkafkaavro.shipping.domain.Shipping;

public interface ShippingRepository {

    Shipping save(Shipping shipping);

    Shipping findByOrderId(Long orderId);
}
