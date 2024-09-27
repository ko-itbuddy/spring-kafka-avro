package com.example.springkafkaavro.common.event;

import com.example.springkafkaavro.common.event.interfaces.DomainEvent;

public class EmptyEvent implements DomainEvent<Void> {

    public static final EmptyEvent INSTANCE = new EmptyEvent();

    private EmptyEvent(){

    }

    @Override
    public void fire() {

    }
}
