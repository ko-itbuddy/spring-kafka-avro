package com.example.springkafkaavro.item.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.springkafkaavro.item.application.ItemService;
import com.example.springkafkaavro.item.application.dto.CreateItemRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

    @MockBean
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("상품 등록")
    class CreateItem {

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
            mockMvc.perform(
                       post("/item")
                           .content(objectMapper.writeValueAsString(request))
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andDo(print())
                   .andExpect(jsonPath("$.code").value("200"))
                   .andExpect(jsonPath("$.status").value("OK"))
                   .andExpect(jsonPath("$.msg").value("OK"));
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
                mockMvc.perform(
                           post("/item")
                               .content(objectMapper.writeValueAsString(request))
                               .contentType(MediaType.APPLICATION_JSON)
                       )
                       .andDo(print())
                       .andExpect(jsonPath("$.code").value("200"))
                       .andExpect(jsonPath("$.status").value("OK"))
                       .andExpect(jsonPath("$.msg").value("OK"));
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
                mockMvc.perform(
                           post("/item")
                               .content(objectMapper.writeValueAsString(request))
                               .contentType(MediaType.APPLICATION_JSON)
                       )
                       .andDo(print())
                       .andExpect(jsonPath("$.code").value("200"))
                       .andExpect(jsonPath("$.status").value("OK"))
                       .andExpect(jsonPath("$.msg").value("OK"));
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
                mockMvc.perform(
                           post("/item")
                               .content(objectMapper.writeValueAsString(request))
                               .contentType(MediaType.APPLICATION_JSON)
                       )
                       .andDo(print())
                       .andExpect(jsonPath("$.code").value("200"))
                       .andExpect(jsonPath("$.status").value("OK"))
                       .andExpect(jsonPath("$.msg").value("OK"));
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

        @Test
        @DisplayName("성공 케이스")
        void success() throws Exception {
            // given
            // when
            // then
            mockMvc.perform(
                       get("/item")
                           .contentType(MediaType.APPLICATION_JSON)
                   )
                   .andDo(print())
                   .andExpect(jsonPath("$.code").value("200"))
                   .andExpect(jsonPath("$.status").value("OK"))
                   .andExpect(jsonPath("$.msg").value("OK"))
                   .andExpect(jsonPath("$.result").isEmpty());
        }
    }
}