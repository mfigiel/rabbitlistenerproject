package com.example.rabbitlistenerproject;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import org.springframework.amqp.core.Message;

@SpringBootApplication
public class RabbitlistenerprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitlistenerprojectApplication.class, args);
    }

    @RabbitListener(queues="CachingHopperGatewayQueue")
    public void receive(Object message) {
        System.out.println("Message = []");
    }
}
