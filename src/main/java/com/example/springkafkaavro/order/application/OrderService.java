package com.example.springkafkaavro.order.application;

import com.example.springkafkaavro.item.application.interfaces.ItemRepository;
import com.example.springkafkaavro.item.domain.Item;
import com.example.springkafkaavro.order.application.dto.CancelOrderRequest;
import com.example.springkafkaavro.order.application.dto.CreateOrderRequest;
import com.example.springkafkaavro.order.application.dto.OrderRequest;
import com.example.springkafkaavro.order.application.dto.OrderResponse;
import com.example.springkafkaavro.order.application.interfaces.OrderRepository;
import com.example.springkafkaavro.order.domain.Order;
import com.example.springkafkaavro.order.repository.entity.OrderEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public List<OrderResponse> createOrder(CreateOrderRequest request) {
        List<Order> orders = request.orders().stream().map(OrderRequest::toDomain).toList();

        Map<Long, Item> itemMap = new HashMap<>();

        itemRepository.findAllById(
                          orders.stream().map(Order::getItemId).toList())
                      .forEach(order -> {
                          itemMap.put(order.getId(), order);
                      });

        for (Order order : orders) {

            if (!itemMap.containsKey(order.getItemId())) {
                throw new IllegalArgumentException(
                    "판매하지 않는 상품의 아이디입니다. itemId: " + order.getItemId());
            }
            order.calculateTotalPrice(itemMap.get(order.getItemId()));
        }

        return orderRepository.saveAll(orders).stream().map(OrderResponse::of).toList();
    }

    public List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream().map(OrderResponse::of).toList();
    }

    public OrderResponse cancelOrder(CancelOrderRequest request){
        Order order = orderRepository.findById(request.orderId());

        order.cancelOrder();

        return OrderResponse.of(orderRepository.save(order));
    }
}
