package com.example.springkafkaavro.common.repository.entity;

import com.example.springkafkaavro.item.domain.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`item`")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    private Long stockQuantity;


    public Item toDomain() {
        return Item.builder()
                   .id(id)
                   .name(name)
                   .price(price)
                   .stockQuantity(stockQuantity)
                   .build();
    }

    public static ItemEntity of(Item item) {
        return ItemEntity.builder()
                         .id(item.getId())
                         .name(item.getName())
                         .price(item.getPrice())
                         .stockQuantity(item.getStockQuantity())
                         .build();
    }
}
