package com.energyresourcesspring.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic devicesEventsTopic(){
        return TopicBuilder.name("devicesEvents")
//                .partitions(1)
//                .compact()
                .config("retention.ms", "10000")
                .build();
    }
    @Bean
    public NewTopic canonicalTopic(){
        return TopicBuilder.name("devicesEventsCanonical")
//                .partitions(1)
//                .compact()
                .build();
    }
}