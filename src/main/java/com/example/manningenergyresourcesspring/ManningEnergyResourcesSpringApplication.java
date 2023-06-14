package com.example.manningenergyresourcesspring;

import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.specific.SpecificData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class ManningEnergyResourcesSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManningEnergyResourcesSpringApplication.class, args);

    }
    @Bean
    public static CompletableFuture<SendResult<String, String>> message(KafkaTemplate<String, String> kafkaTemplate) {

        return kafkaTemplate.send("devicesEvents", "Test message");

    }
//    @Bean
//    CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate){
//        return args -> {
//            for (Integer i = 0; i < 1000001; i++) {
//                kafkaTemplate.send("devicesEvents", i.toString());
//            }};
//    }

}
