package com.example.Moviez.KafkaTests;

import ch.qos.logback.classic.net.server.HardenedLoggingEventInputStream;
import com.example.Moviez.Config.KafkaTopicConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.annotation.Configurations;
import org.springframework.boot.kafka.autoconfigure.KafkaAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestKafkaTopicConfig {

    private ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(KafkaAutoConfiguration.class,KafkaTopicConfig.class)
            .withPropertyValues("spring.kafka.bootstrap-servers=localhost:9092");
    @Test
    void TestIfHasBeanKafkaAdminSetup()
    {

        this.contextRunner
        .run(context -> {
                    assertThat(context).hasBean("kafkaAdmin");
                });

    }
}
