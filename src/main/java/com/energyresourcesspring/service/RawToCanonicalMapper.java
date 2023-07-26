package com.energyresourcesspring.service;

import com.energyresourcesspring.service.generated.CanonicalKeyService;
import com.energyresourcesspring.service.generated.CanonicalValueService;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KeyValueMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.System.currentTimeMillis;

public class RawToCanonicalMapper implements KeyValueMapper<String, Map<String, Object>, KeyValue<CanonicalKeyService, CanonicalValueService>> {

    public KeyValue<CanonicalKeyService, CanonicalValueService> apply(String uuid, Map<String, Object> event){

        CanonicalKeyService key = CanonicalKeyService.newBuilder()
                .setUuid(uuid)
                .build();


        CanonicalValueService.Builder valueBuilder = CanonicalValueService.newBuilder()
                .setUuid(uuid)
                .setRegionId(null)
                .setArrivalTimeMs(currentTimeMillis())
                .setEventTimeMs((Long) event.get("arrival_time_ms"));

        Stream<Map.Entry<String, Object>> stream = event.entrySet().stream();
        Map events = new HashMap<CharSequence, CharSequence>();

        stream.filter(stringObjectEntry -> stringObjectEntry.getKey()!="arrival_time_ms")
                .forEach(k->events.put(k.getKey().toString(),k.getValue().toString()));

        valueBuilder.setEvents(events);

        KeyValue<CanonicalKeyService, CanonicalValueService> result = new KeyValue<>(key, valueBuilder.build());

        return result;
    }
}
