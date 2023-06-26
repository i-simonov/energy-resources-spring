package com.energyresourcesspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

}
