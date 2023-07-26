package com.energyresourcesspring.service;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.avro.AvroSchema;
import org.apache.avro.Schema;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class SchemaRegistryService {

    private static final String SCHEMA_REGISTRY_URL = "http://localhost:8081";

    public static void registerSchema(String topicName, String schemaFilePath) throws IOException {

        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(new File(schemaFilePath));
        AvroSchema avroSchema = new AvroSchema(schema);

        SchemaRegistryClient schemaRegistryClient = new CachedSchemaRegistryClient(SCHEMA_REGISTRY_URL, 100);

        try {
            int schemaId = schemaRegistryClient.register(topicName + "-value", avroSchema);
            System.out.println("Registered schema with id: " + schemaId);
            schemaRegistryClient.updateCompatibility(topicName,"forward_transitive");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
