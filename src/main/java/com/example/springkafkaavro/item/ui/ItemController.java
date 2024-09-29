package com.example.springkafkaavro.item.ui;

import com.example.springkafkaavro.common.ui.dto.ApiResponse;
import com.example.springkafkaavro.item.application.ItemService;
import com.example.springkafkaavro.item.application.dto.CreateItemRequest;
import com.example.springkafkaavro.item.application.dto.ItemResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController implements ItemControllerDocs {

    private final ItemService itemService;

    @PostMapping
    @Override
    public ApiResponse<ItemResponse> createItem(@RequestBody @Valid CreateItemRequest request) {
        return ApiResponse.ok(itemService.createItem(request));
    }

    @GetMapping
    @Override
    public ApiResponse<List<ItemResponse>> getItems() {
        return ApiResponse.ok(itemService.getItems());
    }


}
