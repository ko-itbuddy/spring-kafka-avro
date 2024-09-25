package com.example.springkafkaavro.item.repository.jpa;

import com.example.springkafkaavro.item.repository.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<ItemEntity, Long> {

}
