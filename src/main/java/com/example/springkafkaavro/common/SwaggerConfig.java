package com.example.springkafkaavro.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .components(new Components())
            .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
            .title("KAFKA + AVRO 상품 주문, 배송 시스템") // API의 제목
            .description("간단한 구현") // API에 대한 설명
            .version("0.0.01"); // API의 버전
    }
}

