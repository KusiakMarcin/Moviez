package com.example.Moviez.KafkaTests;


import com.example.Moviez.Config.KafkaProducerConfig;
import com.example.Moviez.Config.KafkaTopicConfig;
import com.example.Moviez.Producers.SomeProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.TestPropertySource;
import scala.Some;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;


@SpringBootTest
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=localhost:9092"
})
public class TestProducerMessage {

    @Autowired
    private KafkaProducerConfig producerConfig;

    @Autowired
    private KafkaTopicConfig topicConfig;

    // Inject your actual producer bean here
    @Autowired
    private SomeProducer someProducer;

    @Test
    public void sendMessage() {
        // Verify that the configurations and producer loaded correctly
        assertNotNull(producerConfig);
        assertNotNull(topicConfig);
        assertNotNull(someProducer);

        // Call your producer method to send a test message
         someProducer.sendMessage("dddd");
    }
}
