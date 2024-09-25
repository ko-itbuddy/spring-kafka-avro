package com.example.springkafkaavro.item.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.example.springkafkaavro.item.application.dto.CreateItemRequest;
import com.example.springkafkaavro.item.application.dto.ItemResponse;
import com.example.springkafkaavro.item.application.interfaces.ItemRepository;
import com.example.springkafkaavro.item.repository.entity.ItemEntity;
import com.example.springkafkaavro.item.repository.jpa.ItemJpaRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;

    @BeforeEach
    void beforeEach() throws Exception {

        ItemEntity item1 = createItemEntity(null, "좋은 상품1", 1000L, 10L);
        ItemEntity item2 = createItemEntity(null, "좋은 상품2", 2000L, 20L);
        ItemEntity item3 = createItemEntity(null, "좋은 상품3", 3000L, 30L);
        ItemEntity item4 = createItemEntity(null, "좋은 상품4", 4000L, 40L);
        ItemEntity item5 = createItemEntity(null, "좋은 상품5", 5000L, 50L);

        itemJpaRepository.saveAll(List.of(item1, item2, item3, item4, item5));
    }

    @AfterEach
    void afterEach() throws Exception {
        itemJpaRepository.deleteAllInBatch();
    }

    @Nested
    @DisplayName("createItemTest")
    class CreateItemTest {

        @Test
        @DisplayName("성공")
        void success() {
            // given
            CreateItemRequest request = new CreateItemRequest("좋은 상품6", 6000L, 60L);
            // when
            ItemResponse itemResult = itemService.createItem(request);

            // then
            assertThat(itemResult)
                .extracting("name", "price", "stockQuantity")
                .containsExactly(request.name(), request.price(), request.stockQuantity());

            assertThat(itemJpaRepository.findAll())
                .extracting("name", "price", "stockQuantity")
                .containsExactly(
                    tuple("좋은 상품1", 1000L, 10L),
                    tuple("좋은 상품2", 2000L, 20L),
                    tuple("좋은 상품3", 3000L, 30L),
                    tuple("좋은 상품4", 4000L, 40L),
                    tuple("좋은 상품5", 5000L, 50L),
                    tuple("좋은 상품6", 6000L, 60L)
                );
        }

    }

    @Nested
    @DisplayName("getItems")
    class getItems {

        @Test
        @DisplayName("성공")
        void success() {
            // given
            // when
            List<ItemResponse> items = itemService.getItems();

            // then
            assertThat(items)
                .extracting("name", "price", "stockQuantity")
                .containsExactly(
                    tuple("좋은 상품1", 1000L, 10L),
                    tuple("좋은 상품2", 2000L, 20L),
                    tuple("좋은 상품3", 3000L, 30L),
                    tuple("좋은 상품4", 4000L, 40L),
                    tuple("좋은 상품5", 5000L, 50L)
                );
        }
    }

    ItemEntity createItemEntity(Long id, String name, Long price, Long stockQuantity) {
        return ItemEntity.builder()
                         .id(id)
                         .name(name)
                         .price(price)
                         .stockQuantity(stockQuantity)
                         .build();

    }

}