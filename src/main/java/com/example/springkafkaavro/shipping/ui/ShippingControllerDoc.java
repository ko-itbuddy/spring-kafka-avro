package com.example.springkafkaavro.shipping.ui;

import com.example.springkafkaavro.common.ui.dto.ApiResponse;
import com.example.springkafkaavro.shipping.application.dto.ChangeShippingRequest;
import com.example.springkafkaavro.shipping.application.dto.ShippingResponse;
import io.swagger.v3.oas.annotations.Operation;

public interface ShippingControllerDoc {

    @Operation(summary = "주문 상품 배송 상태 변경", description = "주문 상품 배송 상태 변경합니다.")
    ApiResponse<ShippingResponse> changeShipping(ChangeShippingRequest request);

    @Operation(summary = "주문 상품의 배송 상태 조회", description = "주문 상품 배송 상태 조회합니다.")
    ApiResponse<ShippingResponse> getShippingByOrderId(Long orderId);
}
