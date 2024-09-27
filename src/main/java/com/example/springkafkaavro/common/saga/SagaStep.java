package com.example.springkafkaavro.common.saga;

import com.example.springkafkaavro.common.event.interfaces.DomainEvent;

public interface SagaStep <T, S extends DomainEvent, U extends DomainEvent>{
    S procss(T data);
    U rollback(T data);
}
