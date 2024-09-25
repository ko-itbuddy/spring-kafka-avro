package com.example.springkafkaavro.item.ui;

import com.example.springkafkaavro.common.ui.dto.ApiResponse;
import com.example.springkafkaavro.item.ui.dto.CreateItemRequest;
import com.example.springkafkaavro.item.ui.dto.ItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;


public interface ItemControllerDocs {

    @Operation(summary = "상품 등록", description = "상품을 등록합니다.")
    ApiResponse<ItemResponse> createItem(CreateItemRequest request);

    @Operation(summary = "상품 목록 조회", description = "모든 상품의 목록을 조회합니다.")
    ApiResponse<List<ItemResponse>> getItems();

}
