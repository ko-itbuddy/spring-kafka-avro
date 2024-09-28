package com.example.springkafkaavro.order.ui;

import com.example.springkafkaavro.common.ui.dto.ApiResponse;
import com.example.springkafkaavro.order.application.dto.CancelOrderRequest;
import com.example.springkafkaavro.order.application.dto.CreateOrderRequest;
import com.example.springkafkaavro.order.application.dto.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.apache.coyote.BadRequestException;

public interface OrderControllerDoc {

    @Operation(summary = "상품 주문", description = "상품을 주문합니다.")
    ApiResponse<List<OrderResponse>> createOrder(CreateOrderRequest request)
        throws BadRequestException;

    @Operation(summary = "주문 목록 조회", description = "주문 목록을 조회합니다.")
    ApiResponse<List<OrderResponse>> getOrders();

    @Operation(summary = "상품 주문 취소", description = "상품 주문 취소합니다.")
    ApiResponse<OrderResponse> cancelOrders(CancelOrderRequest request);
}
