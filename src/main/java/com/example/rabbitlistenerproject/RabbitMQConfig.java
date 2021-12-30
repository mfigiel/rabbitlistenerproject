package com.example.rabbitlistenerproject;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class RabbitMQConfig {

    private static final String MY_QUEUE = "MyQueue";

    @Bean
    Queue myQueue() {
        return new Queue(MY_QUEUE, true);
    }

    @Bean
    Exchange myExchange() {
        return ExchangeBuilder.topicExchange("MyTopicExchange")
                .durable(true)
                .build();
    }

    @Bean
    Binding binding() {
        //return new Binding(MY_QUEUE, Binding.DestinationType.QUEUE, "MyTopicExchange", "topic", null);
        return BindingBuilder
                .bind(myQueue())
                .to(myExchange())
                .with("topic")
                .noargs();
    }

    @Bean
    ConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");

        return cachingConnectionFactory;
    }

//    @Bean
//    MessageListenerContainer messageListenerContainer() {
//        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
//        simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
//        simpleMessageListenerContainer.setQueues(myQueue());
//        simpleMessageListenerContainer.setupMessageListener(new RabbitMQMessageListener());
//        return simpleMessageListenerContainer;
//    }

    @Autowired
    private ConnectionFactory rabbitConnectionFactory;


    //Configuration of RabbitMQ
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(rabbitTemplate());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();

        rabbitTemplate.setConnectionFactory(rabbitConnectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConnectionFactory(rabbitConnectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(5);

        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
