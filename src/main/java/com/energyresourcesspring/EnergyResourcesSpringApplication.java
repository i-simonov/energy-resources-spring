package com.energyresourcesspring;

import com.energyresourcesspring.service.SchemaRegistryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class EnergyResourcesSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergyResourcesSpringApplication.class, args);

    }

//    @Bean
//    public static CompletableFuture<SendResult<String, String>> message(KafkaTemplate<String, String> kafkaTemplate) {
//
//        return kafkaTemplate.send("devicesEvents", "Test message");
//
//    }

//    @Bean
//    CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate){
//        return args -> {
//            for (Integer i = 0; i < 1000001; i++) {
//                kafkaTemplate.send("devicesEvents", i.toString());
//            }};
//    }
    @Bean
    public static void schemaRegistryRegistrator() throws IOException {

        SchemaRegistryService.registerSchema("devicesEvents", "src/main/avro/raw_record.avsc");
        SchemaRegistryService.registerSchema("devicesCanonicalEvents", "src/main/avro/canonical_value.avsc");

    }
}
