package com.example.manningenergyresourcesspring;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(
            topics = "devicesEvents",
            groupId = "groupId"
    )
    void listener(String data){
        System.out.println("Listener receved: " + data.toString());
    }

}