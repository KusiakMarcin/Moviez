package com.example.Moviez.KafkaTests;

import com.example.Moviez.Producers.SomeProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1)
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=localhost:9092"
})
public class KafkaIntegrationTest {

    @Autowired
    private SomeProducer producer;

    @Test
    public void testSendMessage() throws Exception {
        // 1. Send the message via your producer

        producer.sendMessage("Hello Kafka Test");

        // 2. Since Kafka messaging is asynchronous, give the consumer a moment to pick it up
        // In a real robust test, you might use a CountDownLatch or a test spy to assert reception.
        Thread.sleep(5000);

        // If no exceptions are thrown and your consumer logs the output, the flow works!
    }
}