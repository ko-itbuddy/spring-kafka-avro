package com.example.springkafkaavro.shipping.application;

import com.example.springkafkaavro.shipping.application.dto.ChangeShippingRequest;
import com.example.springkafkaavro.shipping.application.dto.ShippingResponse;
import com.example.springkafkaavro.shipping.application.interfaces.ShippingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingService {

    private final ShippingRepository shippingRepository;

    public ShippingResponse changeShipping(ChangeShippingRequest request) {
        return ShippingResponse.of(shippingRepository.save(request.toDomain()));
    }

    public ShippingResponse getShippingByOrderId(Long orderId) {
        return ShippingResponse.of(shippingRepository.findByOrderId(orderId));
    }

}
