package com.energyresourcesspring.service;

import com.energyresourcesspring.service.generated.RawRecordAvroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.core.io.InputStreamResource;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.stream.Collectors;

public class RawToJSONParser implements ValueMapper<RawRecordAvroService, Iterable<Map<String, Object>>> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public Iterable<Map<String, Object>> apply(RawRecordAvroService value){
        return()->{

            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteBufferBackedInputStream(value.getBody())));

            return reader.lines()
                    .map(line ->{
                        try {
                            Map<String, Object> obj = MAPPER.readValue(line, new TypeReference<>() {});
                            obj.put("arrival_time_ms", value.getArrivalTimeMs());

                            return obj;
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .iterator();


        };
    };

}
