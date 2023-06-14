package com.example.manningenergyresourcesspring;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/deviceEvents")
public class DeviceEndpoint {

    private KafkaTemplate<String, String> kafkaTemplate;

    public DeviceEndpoint(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void publish(@RequestBody String messageBody) throws IOException {
//
//        DeviceEvent deviceEvent = new DeviceEvent();

//        DatumReader<DeviceEvent> deviceEventDatumReader = new SpecificDatumReader<DeviceEvent>(DeviceEvent.class);
//        Decoder decoder = DecoderFactory.get().jsonDecoder(DeviceEvent.SCHEMA$,messageBody);
//
//        String message = deviceEventDatumReader.read(null, decoder).toString();
//
//        System.out.println(message);
        kafkaTemplate.send("devicesEvents", messageBody);

    }

}