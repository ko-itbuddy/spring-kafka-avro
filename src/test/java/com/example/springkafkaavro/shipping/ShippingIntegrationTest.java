package com.example.springkafkaavro.shipping;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.springkafkaavro.common.BaseIntegrationTest;
import com.example.springkafkaavro.order.application.dto.CreateOrderRequest;
import com.example.springkafkaavro.order.application.dto.OrderRequest;
import com.example.springkafkaavro.shipping.application.interfaces.ShippingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class ShippingIntegrationTest extends BaseIntegrationTest {

    @Autowired
    ShippingRepository shippingRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {

    }


    @AfterEach
    void afterEach() throws Exception {

    }


    @Nested
    @DisplayName("상품 주문")
    class ChangeShipping {

        @AfterEach
        void afterEach() throws Exception {
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
        }


    }

    @Nested
    @DisplayName("주문 목록 조회")
    class GetShippingByOrderId {
        // TODO: 테스트 작성

        @BeforeEach
        void beforeEach() throws Exception {

        }

        @AfterEach
        void afterEach() throws Exception {
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