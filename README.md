# EnergyResourcesSpring

EnergyResourcesSpring is a Spring Boot application designed for managing IoT devices data through Kafka Streams. It uses Apache Avro for serialization and deserialization.

## Key Features

1. **DeviceEventAvroService** - A service for managing device events, based on the Avro schema.
2. **Kafka Consumer and Producer configurations** - Configurations for consuming and producing messages from/to Kafka.
3. **Kafka Stream configuration** - Configuration for managing Kafka Streams.
4. **DeviceEventsEndpoint** - Endpoint for publishing device events to the Kafka topic.
5. **DeviceStateEndpoint** - Endpoint for getting the current state of the device.
6. **DeviceStateRepository** - A JPA repository for managing the device states in the database.
7. **DeviceStateService** - A service for managing device states.
8. **KafkaConsumerService** - A service for consuming device events from Kafka.
9. **KafkaDeviceEventsStreamListenerService** - A service for consuming device events from Kafka streams.

## How to Run

1. Clone the project.
2. Navigate to the project directory.
3. Run `mvn spring-boot:run`.

## Project Structure

This project mainly consists of following packages:

- `com.example.manningenergyresourcesspring.service` - Contains service interfaces and their implementations.
- `com.energyresourcesspring.config` - Contains all the configurations including Kafka Producer and Consumer configurations.
- `com.energyresourcesspring.controller` - Contains the REST controllers for handling device events and states.
- `com.energyresourcesspring.model` - Contains the DeviceState entity.
- `com.energyresourcesspring.repository` - Contains the JPA repository.
- `com.energyresourcesspring.service.impl` - Contains the service implementations.
- `com.energyresourcesspring.service` - Contains the service interfaces.

## Application Workflow

1. A new device event is published via the `/api/v1/deviceEvents/{device_id}` endpoint, where `{device_id}` is the unique identifier of the device. This event message is published to the Kafka topic `devicesEvents`.
2. The Kafka Consumer and Stream Listener services listen to the `devicesEvents` topic for any incoming device events.
3. When a device event is received, it is deserialized using the Avro schema and the device's state is updated accordingly.
4. The updated state of the device can be fetched using the `/api/v1/deviceState/{device_id}` endpoint, where `{device_id}` is the unique identifier of the device.


## Usage

The application provides the following REST APIs:

- `POST /api/v1/deviceEvents/{device_id}`: Publish a new device event.
- `GET /api/v1/deviceState/{device_id}`: Get the current state of a device.

## Dependencies

This project uses the following dependencies:

- Spring Boot
- Spring Kafka
- Apache Kafka
- Apache Avro
- PostgreSQL
- Spring Data JPA
- Lombok

## Build & Run

This project uses Maven for dependency management. To build the project, run `mvn clean install`. To start the application, use `mvn spring-boot:run`.

## Note

Please ensure that you have a running instance of Kafka and PostgreSQL and the connection details are correctly configured in the application.properties file.
