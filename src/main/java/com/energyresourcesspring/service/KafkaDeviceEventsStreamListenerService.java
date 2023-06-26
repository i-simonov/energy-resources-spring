package com.energyresourcesspring.service;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class KafkaDeviceEventsStreamListenerService {

    private DeviceStateService deviceStateService;

    public KafkaDeviceEventsStreamListenerService(DeviceStateService deviceStateService) {
        this.deviceStateService = deviceStateService;
    }

    @Bean
    public KStream<String, DeviceEventAvroService> kStreamJson(StreamsBuilder kStreamBuilder) {

        final Serde<DeviceEventAvroService> deviceEventAvroServiceSerde = new JsonSerde<>(DeviceEventAvroService.class);

        //        final Map<String, String> serdeConfig = Collections.singletonMap(
//                "schema.registry.url", ""
//        );
//
//        final Serde<DeviceEventAvroService> deviceEventAvroServiceSerde = new SpecificAvroSerde<>();

//        deviceEventAvroServiceSerde.configure(serdeConfig, false);

        KStream<String, DeviceEventAvroService> stream = kStreamBuilder.stream("devicesEvents",
                Consumed.with(Serdes.String(), deviceEventAvroServiceSerde));//new JsonSerde<>(DeviceState.class)));

        stream
//                .groupByKey(Grouped.with(Serdes.String(), deviceEventAvroServiceSerde))
//                .reduce((aggValue, newValue) -> newValue)
//                .toStream()
                .foreach((key, value) -> deviceStateService.saveDeviceState(key, value));

        return stream;
    }

}
