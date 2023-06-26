package com.energyresourcesspring.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/deviceEvents/{device_id}")
public class DeviceEventsEndpoint {

    private KafkaTemplate<String, String> kafkaTemplate;

    public DeviceEventsEndpoint(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void publish(@PathVariable("device_id") String device_id, @RequestBody String messageBody) throws IOException {

        kafkaTemplate.send("devicesEvents", device_id, messageBody);

    }

}