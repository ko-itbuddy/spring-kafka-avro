package com.example.springkafkaavro.common.repository;

import com.example.springkafkaavro.item.application.interfaces.ItemRepository;
import com.example.springkafkaavro.item.domain.Item;
import com.example.springkafkaavro.common.repository.entity.ItemEntity;
import com.example.springkafkaavro.common.repository.jpa.ItemJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final ItemJpaRepository itemJpaRepository;


    @Override
    public List<Item> findAll() {
        return itemJpaRepository.findAll().stream().map(ItemEntity::toDomain).toList();
    }

    @Override
    public Item save(Item item) {
        return itemJpaRepository.save(ItemEntity.of(item)).toDomain();
    }


    @Override
    public List<Item> findAllById(List<Long> ids) {
        return itemJpaRepository.findAllById(ids).stream().map(ItemEntity::toDomain).toList();
    }
}
