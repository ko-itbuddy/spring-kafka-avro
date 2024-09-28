package com.example.springkafkaavro.order.repository;

import com.example.springkafkaavro.order.application.interfaces.OrderRepository;
import com.example.springkafkaavro.order.domain.Order;
import com.example.springkafkaavro.order.repository.entity.OrderEntity;
import com.example.springkafkaavro.order.repository.jpa.OrderJpaRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;


    @Override
    public List<Order> saveAll(List<Order> orders) {
        List<OrderEntity> orderEntities = orders.stream().map(OrderEntity::of).toList();
        return orderJpaRepository.saveAll(orderEntities)
                                 .stream()
                                 .map(OrderEntity::toDomain)
                                 .toList();
    }

    @Override
    public List<Order> findAll() {
        return orderJpaRepository.findAll().stream().map(OrderEntity::toDomain).toList();
    }

    @Override
    public Order findById(Long orderId) {
        return orderJpaRepository.findById(orderId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                    "해당 주문 아이디에 해당하는 주문 정보가 존재하지 않습니다. orderId: " + orderId)).toDomain();
    }

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(OrderEntity.of(order)).toDomain();
    }
}
