package com.example.rabbitlistenerproject;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.util.List;

public class RabbitMQMessageListener {//implements MessageListener {
//
//
//    @Override
//    public void onMessage(Message message) {
//        System.out.println("Message = [" + new String(message.getBody()) + "]");
//    }
//
//    @Override
//    public void containerAckMode(AcknowledgeMode mode) {
//
//    }
//
//    @Override
//    public void onMessageBatch(List<Message> messages) {
//
//    }
}
