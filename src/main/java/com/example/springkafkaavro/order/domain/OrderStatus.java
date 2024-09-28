package com.example.springkafkaavro.order.domain;

public enum OrderStatus {
    ORDERED, // 주문됨
    REFUND, // 환불 요청됨
    DELIVERED,// 배송 완료
    CANCELED // 취소
}
