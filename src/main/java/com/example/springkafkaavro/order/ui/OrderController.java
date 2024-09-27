package com.example.springkafkaavro.order.ui;

import com.example.springkafkaavro.common.ui.dto.ApiResponse;
import com.example.springkafkaavro.order.application.dto.CreateOrderRequest;
import com.example.springkafkaavro.order.application.dto.OrderResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/order")
@RestController
@RequiredArgsConstructor
public class OrderController implements OrderControllerDoc{

    @PostMapping("")
    public ApiResponse<List<OrderResponse>> createOrder(@RequestBody @Validated CreateOrderRequest request){
        return ApiResponse.ok(new ArrayList<>());
    }

    @GetMapping("")
    public ApiResponse<List<OrderResponse>> getOrders(){
        return ApiResponse.ok(new ArrayList<>());
    }
}
