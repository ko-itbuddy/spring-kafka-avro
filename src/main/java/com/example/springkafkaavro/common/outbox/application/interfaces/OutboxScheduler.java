package com.example.springkafkaavro.common.outbox.application.interfaces;

public interface OutboxScheduler {
    void processOutboxMessage();
}
