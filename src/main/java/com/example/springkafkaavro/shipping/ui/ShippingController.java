package com.example.springkafkaavro.shipping.ui;

import com.example.springkafkaavro.common.ui.dto.ApiResponse;
import com.example.springkafkaavro.shipping.application.ShippingService;
import com.example.springkafkaavro.shipping.application.dto.ChangeShippingRequest;
import com.example.springkafkaavro.shipping.application.dto.GetShippingRequest;
import com.example.springkafkaavro.shipping.application.dto.ShippingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shippings")
@RequiredArgsConstructor
public class ShippingController implements ShippingControllerDoc {

    private final ShippingService shippingService;

    @PutMapping("/")
    public ApiResponse<ShippingResponse> changeShipping(
        @RequestBody @Valid ChangeShippingRequest request) {
        return ApiResponse.ok(shippingService.changeShipping(request));
    }

    @GetMapping("/orders/{orderId}")
    public ApiResponse<ShippingResponse> getShippingByOrderId(@PathVariable("orderId") Long orderId) {
        return ApiResponse.ok(shippingService.getShippingByOrderId(orderId));
    }
}
