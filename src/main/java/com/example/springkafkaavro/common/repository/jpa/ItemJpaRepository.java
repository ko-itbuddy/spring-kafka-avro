package com.example.springkafkaavro.common.repository.jpa;

import com.example.springkafkaavro.common.repository.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<ItemEntity, Long> {

}
