package com.example.Moviez.Producers;


import com.example.Moviez.Config.KafkaTopicConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
public class SomeProducer {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;


    public void sendMessage(String msg){
        kafkaTemplate.send("baeldung",msg);
    }


}
