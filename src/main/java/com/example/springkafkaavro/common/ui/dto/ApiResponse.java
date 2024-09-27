package com.example.springkafkaavro.common.ui.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Schema(description = "모든 응답의 기본객체")
@NoArgsConstructor
public class ApiResponse<T> {

    @Schema(description = "응답 코드")
    private int code;
    @Schema(description = "응답 상태")
    private HttpStatus status;
    @Schema(description = "응답 메시지")
    private String msg;
    @Schema(description = "응답 결과")
    private T result;

    public ApiResponse(HttpStatus status, String msg, T result){
        this.code = status.value();
        this.status = status;
        this.msg = msg;
        this.result = result;
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, String msg, T result){
        return new ApiResponse<T>(httpStatus, msg, result);
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T result) {
        return of(httpStatus, httpStatus.name(), result);
    }

    public static <T> ApiResponse<T> ok(T result) {
        return of(HttpStatus.OK, result);
    }

}
