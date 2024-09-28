package com.example.springkafkaavro.order.application.interfaces;

import com.example.springkafkaavro.order.domain.Order;
import java.util.List;

public interface OrderRepository {

    List<Order> saveAll(List<Order> orders);

    List<Order> findAll();

    Order findById(Long orderId);

    Order save(Order order);

}
