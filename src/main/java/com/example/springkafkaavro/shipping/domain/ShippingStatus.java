package com.example.springkafkaavro.shipping.domain;

public enum ShippingStatus {
    READY, //  배송 준비 중
    IN_TRANSIT, //  배송 중
    OUT_FOR_DELIVERY, //  배송 출발
    DELIVERED, //  배송 완료
    RETURNED, //  반송됨
    CANCELLED //  취소됨
}

