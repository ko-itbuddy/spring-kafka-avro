package com.example.springkafkaavro.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.springkafkaavro.common.BaseIntegrationTest;
import com.example.springkafkaavro.common.EntityCreator;
import com.example.springkafkaavro.common.ui.dto.ApiResponse;
import com.example.springkafkaavro.item.repository.entity.ItemEntity;
import com.example.springkafkaavro.item.repository.jpa.ItemJpaRepository;
import com.example.springkafkaavro.order.application.dto.CreateOrderRequest;
import com.example.springkafkaavro.order.application.dto.OrderRequest;
import com.example.springkafkaavro.order.application.dto.OrderResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

class OrderIntegrationTest extends BaseIntegrationTest {

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void beforeEach() throws Exception {

        ItemEntity item1 = EntityCreator.createItemEntity(null, "좋은 상품1", 1000L, 10L);
        ItemEntity item2 = EntityCreator.createItemEntity(null, "좋은 상품2", 2000L, 20L);
        ItemEntity item3 = EntityCreator.createItemEntity(null, "좋은 상품3", 3000L, 30L);
        ItemEntity item4 = EntityCreator.createItemEntity(null, "좋은 상품4", 4000L, 40L);
        ItemEntity item5 = EntityCreator.createItemEntity(null, "좋은 상품5", 5000L, 50L);

        itemJpaRepository.saveAll(List.of(item1, item2, item3, item4, item5));
    }

    @AfterEach
    void afterEach() throws Exception {
        itemJpaRepository.deleteAllInBatch();
    }

    @Nested
    @DisplayName("상품 주문")
    class CreateOrder {

        private ApiResponse<List<OrderResponse>> convertMvcResultToApiResponse(
            MvcResult mvcResult)
            throws UnsupportedEncodingException, JsonProcessingException {
            return objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<ApiResponse<List<OrderResponse>>>() {
                });
        }

        private List<OrderResponse> successTestAndReturnOrderResponse(CreateOrderRequest request)
            throws Exception {
            MvcResult result = mockMvc.perform(
                                          post("/order")
                                              .content(objectMapper.writeValueAsString(request))
                                              .contentType(MediaType.APPLICATION_JSON)
                                      )
                                      .andDo(print())
                                      .andExpect(jsonPath("$.code").value("200"))
                                      .andExpect(jsonPath("$.status").value("OK"))
                                      .andExpect(jsonPath("$.msg").value("OK"))
                                      .andReturn();

            List<OrderResponse> response = convertMvcResultToApiResponse(result).getResult();

            for (int idx = 0; idx < response.size(); idx++) {
                assertThat(response.get(idx)).extracting("itemId", "quantity")
                                             .containsExactly(
                                                 request.orders().get(idx).itemId(),
                                                 request.orders().get(idx).quantity()
                                             );
            }
            return response;
        }

        @Test
        @DisplayName("정상")
        void success() throws Exception {
            // given
            CreateOrderRequest request = new CreateOrderRequest(List.of(
                new OrderRequest(1L, 1L),
                new OrderRequest(2L, 2L),
                new OrderRequest(3L, 3L),
                new OrderRequest(4L, 4L),
                new OrderRequest(5L, 5L)
            ));
            // when
            // then
            successTestAndReturnOrderResponse(request);
        }

        @Test
        @DisplayName("주문 상품 목록 null")
        void withNullOrders() throws Exception {
            // given
            CreateOrderRequest request = new CreateOrderRequest(null);
            // when
            // then
            mockMvc.perform(
                       post("/order")
                           .content(objectMapper.writeValueAsString(request))
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andDo(print())
                   .andExpect(jsonPath("$.code").value("400"))
                   .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                   .andExpect(jsonPath("$.msg").value("주문 요청 목록은 null이면 안됩니다."))
                   .andExpect(jsonPath("$.result").isEmpty());
        }

        @Test
        @DisplayName("주문 상품 목록 0건")
        void withEmptyOrders() throws Exception {
            // given
            CreateOrderRequest request = new CreateOrderRequest(new ArrayList<>());
            // when
            // then
            mockMvc.perform(
                       post("/order")
                           .content(objectMapper.writeValueAsString(request))
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andDo(print())
                   .andExpect(jsonPath("$.code").value("400"))
                   .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                   .andExpect(jsonPath("$.msg").value("주문 요청 목록은 1개이상 존재해야 합니다."))
                   .andExpect(jsonPath("$.result").isEmpty());
        }

    }

    @Nested
    @DisplayName("주문 목록 조회")
    class GetOrders {
        // TODO: 테스트 작성

        @BeforeEach
        void beforeEach() throws Exception {

        }

        @AfterEach
        void afterEach() throws Exception {
            itemJpaRepository.deleteAllInBatch();
        }

        @Test
        @DisplayName("정상")
        void success() {
            // given
            // when
            // then

        }
    }
}