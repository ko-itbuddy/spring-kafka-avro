package com.example.springkafkaavro.item.ui;

import com.example.springkafkaavro.common.ui.dto.ApiResponse;
import com.example.springkafkaavro.item.ui.dto.CreateItemRequest;
import com.example.springkafkaavro.item.ui.dto.ItemResponse;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController implements ItemControllerDocs {

    @PostMapping
    @Override
    public ApiResponse<ItemResponse> createItem(@RequestBody @Valid CreateItemRequest request) {



        return ApiResponse.ok(new ItemResponse(-1L, request.name(), request.price(),
            request.stockQuantity()));
    }

    @GetMapping
    @Override
    public ApiResponse<List<ItemResponse>> getItems() {
        return ApiResponse.ok(new ArrayList<>());
    }


}
