package com.example.springkafkaavro.item.application;

import com.example.springkafkaavro.item.application.dto.CreateItemRequest;
import com.example.springkafkaavro.item.application.dto.ItemResponse;
import com.example.springkafkaavro.item.application.interfaces.ItemRepository;
import com.example.springkafkaavro.item.domain.Item;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemResponse createItem(CreateItemRequest request) {
        Item item = request.toDomain();
        return ItemResponse.of(itemRepository.save(item));
    }

    public List<ItemResponse> getItems() {
        return itemRepository.findAll().stream().map(ItemResponse::of).toList();
    }
}
