package com.example.springkafkaavro.common.event.interfaces;

public interface DomainEvent<T> {
    void fire();
}
