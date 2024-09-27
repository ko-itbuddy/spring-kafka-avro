package com.example.springkafkaavro.common;

import com.example.springkafkaavro.item.repository.entity.ItemEntity;

public class EntityCreator {

    public static ItemEntity createItemEntity(Long id, String name, Long price, Long stockQuantity) {
        return ItemEntity.builder()
                         .id(id)
                         .name(name)
                         .price(price)
                         .stockQuantity(stockQuantity)
                         .build();

    }
}
