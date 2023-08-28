package com.energyresourcesspring.controller;

import com.energyresourcesspring.service.generated.RawRecordAvroService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import static java.lang.System.currentTimeMillis;

@RestController
@RequestMapping("api/v1/deviceEvents/{device_id}")
public class DeviceEventsEndpoint {

    private KafkaTemplate<String, RawRecordAvroService> kafkaTemplate;

    public DeviceEventsEndpoint(KafkaTemplate<String, RawRecordAvroService> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void publish(@PathVariable("device_id") String device_id, @RequestBody String messageBody) {

        RawRecordAvroService rawMessage = new RawRecordAvroService();

        rawMessage.setArrivalTimeMs(currentTimeMillis());
        rawMessage.setUuid(device_id);
        rawMessage.setBody(str_to_bb(messageBody,Charset.forName("UTF-8")));

        kafkaTemplate.send("devicesEvents", device_id, rawMessage);

    }
    private static ByteBuffer str_to_bb(String msg, Charset charset){
        return ByteBuffer.wrap(msg.getBytes(charset));
    }
}