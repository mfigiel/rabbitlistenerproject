package com.example.rabbitlistenerproject;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Listener {

    @RabbitListener(queues="MyQueue")
    public void receive(Object message) {
        System.out.println("Message = []");
    }
}
