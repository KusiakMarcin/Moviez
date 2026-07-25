package com.example.Moviez.Consumers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class SomeConsumer {

    private static final Logger log = LoggerFactory.getLogger(SomeConsumer.class);

    @KafkaListener(topics = "baeldung", groupId = "1")
    public void listenGroupFoo(String message) {
    log.info("recived message:{}",message);
    }
}
