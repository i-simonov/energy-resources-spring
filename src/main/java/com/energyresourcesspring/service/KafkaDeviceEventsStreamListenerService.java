package com.energyresourcesspring.service;

import com.energyresourcesspring.service.generated.CanonicalKeyService;
import com.energyresourcesspring.service.generated.CanonicalValueService;
import com.energyresourcesspring.service.generated.RawRecordAvroService;
import org.apache.avro.util.Utf8;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.Map;


@Configuration
public class KafkaDeviceEventsStreamListenerService {
    private static final Logger log = LoggerFactory.getLogger(KafkaDeviceEventsStreamListenerService.class);

    private DeviceStateService deviceStateService;

    public KafkaDeviceEventsStreamListenerService(DeviceStateService deviceStateService) {
        this.deviceStateService = deviceStateService;
    }

    @Bean
    public KStream<String,  RawRecordAvroService> kStreamJson(StreamsBuilder kStreamBuilder) {

        final Serde<RawRecordAvroService> rawRecordAvroSerde = new SpecificAvroSerde<>();
        final Serde<CanonicalKeyService> canonicalKeyRecordAvroSerde = new SpecificAvroSerde<>();
        final Serde<CanonicalValueService> canonicalValueRecordAvroSerde = new SpecificAvroSerde<>();
        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", "http://localhost:8081");

        rawRecordAvroSerde.configure(serdeConfig, false);
        canonicalKeyRecordAvroSerde.configure(serdeConfig, true);
        canonicalValueRecordAvroSerde.configure(serdeConfig, false);

        KStream<String, RawRecordAvroService> sourceStream = kStreamBuilder.stream("devicesEvents", Consumed.with(Serdes.String(), rawRecordAvroSerde));

        sourceStream
                .flatMapValues(new RawToJSONParser())
                .map(new RawToCanonicalMapper())
                .to("devicesCanonicalEvents", Produced.with(canonicalKeyRecordAvroSerde, canonicalValueRecordAvroSerde));


        return sourceStream;
    }

    @Bean
    public KStream<CanonicalKeyService,  CanonicalValueService> kStreamCanonical(StreamsBuilder kStreamBuilder) {

        final Serde<CanonicalKeyService> canonicalKeyRecordAvroSerde = new SpecificAvroSerde<>();
        final Serde<CanonicalValueService> canonicalValueRecordAvroSerde = new SpecificAvroSerde<>();
        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", "http://localhost:8081");

        canonicalKeyRecordAvroSerde.configure(serdeConfig, true);
        canonicalValueRecordAvroSerde.configure(serdeConfig, false);

        KStream<CanonicalKeyService,  CanonicalValueService> sourceStream = kStreamBuilder.stream("devicesCanonicalEvents", Consumed.with(canonicalKeyRecordAvroSerde, canonicalValueRecordAvroSerde));

        sourceStream
                .filter((k,v)-> v.getEvents().containsKey(new Utf8("charging")))
                .foreach((k,v)-> deviceStateService.saveDeviceState(k.getUuid().toString(), Long.parseLong(v.getEvents().get(new Utf8("charging")).toString())>0));

        return sourceStream;
    }

}

