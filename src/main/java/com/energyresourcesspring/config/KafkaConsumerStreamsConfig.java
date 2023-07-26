package com.energyresourcesspring.config;

import org.apache.kafka.streams.StreamsConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Configuration
@EnableKafkaStreams
public class KafkaConsumerStreamsConfig {

    @Bean
    public StreamsConfig streamsConfig(KafkaProperties properties){
        return new StreamsConfig(properties.buildStreamsProperties());
    }

}
