package com.kennywgx.config;

import com.kennywgx.service.MQDemoService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RabbitMQDemoConfiguration.Receiver.class)
public class RabbitMQDemoConfiguration {
    @Bean
    public Queue test() {
        return new Queue("test.queue");
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    @RabbitListener(queues = "test.queue", containerFactory = "rabbitListenerContainerFactory")
    public class Receiver {
        @Autowired
        private MQDemoService service;

        @RabbitHandler
        public void process(MQDemoService.ContentDto data) {
            service.consume(data);
        }
    }
}
