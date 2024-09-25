package com.example.springkafkaavro.item.application.interfaces;

import com.example.springkafkaavro.item.domain.Item;
import java.util.List;

public interface ItemRepository {

    List<Item> findAll();

    Item save(Item item);
}
