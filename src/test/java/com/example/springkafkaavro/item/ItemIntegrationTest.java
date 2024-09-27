package com.example.springkafkaavro.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.springkafkaavro.common.BaseIntegrationTest;
import com.example.springkafkaavro.common.ui.dto.ApiResponse;
import com.example.springkafkaavro.item.application.ItemService;
import com.example.springkafkaavro.item.application.dto.CreateItemRequest;
import com.example.springkafkaavro.item.application.dto.ItemResponse;
import com.example.springkafkaavro.item.application.interfaces.ItemRepository;
import com.example.springkafkaavro.item.repository.entity.ItemEntity;
import com.example.springkafkaavro.item.repository.jpa.ItemJpaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

class ItemIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("상품 등록")
    class CreateItem {

        private ItemResponse successTestAndReturnItemResponse(CreateItemRequest request)
            throws Exception {
            MvcResult result = mockMvc.perform(
                                          post("/item")
                                              .content(objectMapper.writeValueAsString(request))
                                              .contentType(MediaType.APPLICATION_JSON)
                                      )
                                      .andDo(print())
                                      .andExpect(jsonPath("$.code").value("200"))
                                      .andExpect(jsonPath("$.status").value("OK"))
                                      .andExpect(jsonPath("$.msg").value("OK"))
                                      .andReturn();

            ItemResponse response = convertMvcResultToApiResponse(result).getResult();

            assertThat(response).extracting("name", "price", "stockQuantity")
                                .containsExactly(request.name(), request.price(),
                                    request.stockQuantity());

            return response;
        }

        private ApiResponse<ItemResponse> convertMvcResultToApiResponse(MvcResult mvcResult)
            throws IOException {
            return objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<ApiResponse<ItemResponse>>() {
                });
        }

        @Test
        @DisplayName("성공 케이스")
        void success() throws Exception {
            // given
            String name = "좋은 상품";
            Long price = 5000L;
            Long stockQuantity = 10L;
            CreateItemRequest request = new CreateItemRequest(name, price, stockQuantity);
            // when
            // then

            ItemResponse itemResponse = successTestAndReturnItemResponse(request);


        }

        @Nested
        @DisplayName("상품 이름 테스트")
        class ItemNameTest {
            @DisplayName("상품 이름 null")
            @Test
            void withKoreanNameNull() throws Exception {
                // given
                String name = null;
                Long price = 5000L;
                Long stockQuantity = 10L;
                CreateItemRequest request = new CreateItemRequest(name, price, stockQuantity);
                // when
                // then
                mockMvc.perform(
                           post("/item")
                               .content(objectMapper.writeValueAsString(request))
                               .contentType(MediaType.APPLICATION_JSON)
                       )
                       .andDo(print())
                       .andExpect(jsonPath("$.code").value("400"))
                       .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                       .andExpect(jsonPath("$.msg").value("상품 이름은 null 이면 안됩니다."))
                       .andExpect(jsonPath("$.result").isEmpty());
            }

            @DisplayName("상품 이름 빈문자열")
            @Test
            void withKoreanNameEmpty() throws Exception {
                // given
                String name = "";
                Long price = 5000L;
                Long stockQuantity = 10L;
                CreateItemRequest request = new CreateItemRequest(name, price, stockQuantity);
                // when
                // then
                mockMvc.perform(
                           post("/item")
                               .content(objectMapper.writeValueAsString(request))
                               .contentType(MediaType.APPLICATION_JSON)
                       )
                       .andDo(print())
                       .andExpect(jsonPath("$.code").value("400"))
                       .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                       .andExpect(jsonPath("$.msg").value("상품 이름은 최소 3글자 최대 255글자입니다."))
                       .andExpect(jsonPath("$.result").isEmpty());
            }


            @DisplayName("상품 이름 한글 입력 테스트 (길이 충족)")
            @ParameterizedTest
            @MethodSource("provideKoreanNameParametersInRange")
            void withKoreanNameOutInRage(String koreanWord, int repeatCnt) throws Exception {
                // given
                String name = koreanWord.repeat(repeatCnt);
                Long price = 5000L;
                Long stockQuantity = 10L;
                CreateItemRequest request = new CreateItemRequest(name, price, stockQuantity);
                // when
                // then
                ItemResponse itemResponse = successTestAndReturnItemResponse(request);

            }

            private static Stream<Arguments> provideKoreanNameParametersInRange() {
                return Stream.of(
                    Arguments.of("뷁", 3),
                    Arguments.of("닭", 3),
                    Arguments.of("굵", 3),
                    Arguments.of("뷁", 255),
                    Arguments.of("닭", 255),
                    Arguments.of("굵", 255)
                );
            }

            @DisplayName("상품 이름 한글 입력 테스트 (길이 미충족)")
            @ParameterizedTest
            @MethodSource("provideKoreanNameParametersOutOfRange")
            void withKoreanNameOutOfRange(String koreanWord, int repeatCnt) throws Exception {
                // given
                String name = koreanWord.repeat(repeatCnt);
                Long price = 5000L;
                Long stockQuantity = 10L;
                CreateItemRequest request = new CreateItemRequest(name, price, stockQuantity);
                // when
                // then
                mockMvc.perform(
                           post("/item")
                               .content(objectMapper.writeValueAsString(request))
                               .contentType(MediaType.APPLICATION_JSON)
                       )
                       .andDo(print())
                       .andExpect(jsonPath("$.code").value("400"))
                       .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                       .andExpect(jsonPath("$.msg").value("상품 이름은 최소 3글자 최대 255글자입니다."))
                       .andExpect(jsonPath("$.result").isEmpty());
            }
            private static Stream<Arguments> provideKoreanNameParametersOutOfRange() {
                return Stream.of(
                    Arguments.of("뷁", 2),
                    Arguments.of("닭", 2),
                    Arguments.of("굵", 2),
                    Arguments.of("뷁", 256),
                    Arguments.of("닭", 256),
                    Arguments.of("굵", 256)
                );
            }

        }

        @Nested
        @DisplayName("상품 가격 테스트")
        class ItemPriceTest {
            @DisplayName("상품 가격 null")
            @Test
            void withPriceNull() throws Exception {
                // given
                String name = "좋은 상품";
                Long price = null;
                Long stockQuantity = 10L;
                CreateItemRequest request = new CreateItemRequest(name, price, stockQuantity);
                // when
                // then
                mockMvc.perform(
                           post("/item")
                               .content(objectMapper.writeValueAsString(request))
                               .contentType(MediaType.APPLICATION_JSON)
                       )
                       .andDo(print())
                       .andExpect(jsonPath("$.code").value("400"))
                       .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                       .andExpect(jsonPath("$.msg").value("상품 가격은 null 이면 안됩니다."))
                       .andExpect(jsonPath("$.result").isEmpty());
            }

            @DisplayName("상품 가격 범위 내")
            @ParameterizedTest
            @ValueSource(longs = {1, 500000000})
            void withPriceInRange(Long price) throws Exception {
                // given
                String name = "좋은 상품";

                Long stockQuantity = 10L;
                CreateItemRequest request = new CreateItemRequest(name, price, stockQuantity);
                // when
                // then
                ItemResponse itemResponse = successTestAndReturnItemResponse(request);
            }

            @DisplayName("상품 가격 범위 외")
            @ParameterizedTest
            @ValueSource(longs = {0, 500000001})
            void withPriceOutOfRange(Long price) throws Exception {
                // given
                String name = "좋은 상품";

                Long stockQuantity = 10L;
                CreateItemRequest request = new CreateItemRequest(name, price, stockQuantity);
                // when
                // then
                mockMvc.perform(
                           post("/item")
                               .content(objectMapper.writeValueAsString(request))
                               .contentType(MediaType.APPLICATION_JSON)
                       )
                       .andDo(print())
                       .andExpect(jsonPath("$.code").value("400"))
                       .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                       .andExpect(jsonPath("$.msg").value("상품 가격범위는 1원~ 5억원 입니다."))
                       .andExpect(jsonPath("$.result").isEmpty());
            }

        }

        @Nested
        @DisplayName("상품 재고 수량 테스트")
        class ItemStockQuantityTest {
            @DisplayName("상품 재고 수량 null")
            @Test
            void withStockQuantityNUll() throws Exception {
                // given
                String name = "좋은 상품";
                Long price = 5000L;
                Long stockQuantity = null;
                CreateItemRequest request = new CreateItemRequest(name, price, stockQuantity);
                // when
                // then
                mockMvc.perform(
                           post("/item")
                               .content(objectMapper.writeValueAsString(request))
                               .contentType(MediaType.APPLICATION_JSON)
                       )
                       .andDo(print())
                       .andExpect(jsonPath("$.code").value("400"))
                       .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                       .andExpect(jsonPath("$.msg").value("상품 재고 수량은 null 이면 안됩니다."))
                       .andExpect(jsonPath("$.result").isEmpty());
            }

            @DisplayName("상품 가격 범위 내")
            @ParameterizedTest
            @ValueSource(longs = {0,1})
            void withStockQuantityPriceInRange(Long stockQuantity) throws Exception {
                // given
                String name = "좋은 상품";
                Long price = 5000L;
                CreateItemRequest request = new CreateItemRequest(name, price, stockQuantity);
                // when
                // then
                ItemResponse itemResponse = successTestAndReturnItemResponse(request);
            }

            @DisplayName("상품 가격 범위 외")
            @ParameterizedTest
            @ValueSource(longs = {-1, -1000})
            void withStockQuantityOutOfRange(Long stockQuantity) throws Exception {
                // given
                String name = "좋은 상품";
                Long price = 5000L;
                CreateItemRequest request = new CreateItemRequest(name, price, stockQuantity);
                // when
                // then
                mockMvc.perform(
                           post("/item")
                               .content(objectMapper.writeValueAsString(request))
                               .contentType(MediaType.APPLICATION_JSON)
                       )
                       .andDo(print())
                       .andExpect(jsonPath("$.code").value("400"))
                       .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                       .andExpect(jsonPath("$.msg").value("상품 재고 수량은 0이상의 수입니다."))
                       .andExpect(jsonPath("$.result").isEmpty());
            }

        }
    }

    @Nested
    @DisplayName("상품 목록 조회")
    class GetItems {

        ItemEntity createItemEntity(Long id, String name, Long price, Long stockQuantity) {
            return ItemEntity.builder()
                             .id(id)
                             .name(name)
                             .price(price)
                             .stockQuantity(stockQuantity)
                             .build();

        }

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

        private ApiResponse<List<ItemResponse>> convertMvcResultToApiResponse(MvcResult mvcResult)
            throws UnsupportedEncodingException, JsonProcessingException {
            return objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<ApiResponse<List<ItemResponse>>>() {
                });
        }

        @Test
        @DisplayName("성공 케이스")
        void success() throws Exception {
            // given
            // when
            // then
            MvcResult result = mockMvc.perform(
                       get("/item")
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andDo(print())
                   .andExpect(jsonPath("$.code").value("200"))
                   .andExpect(jsonPath("$.status").value("OK"))
                   .andExpect(jsonPath("$.msg").value("OK"))
                                      .andReturn();

            List<ItemResponse> itemResponsesList = convertMvcResultToApiResponse(
                result).getResult();

            assertThat(itemResponsesList)
                .hasSize(5)
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
}