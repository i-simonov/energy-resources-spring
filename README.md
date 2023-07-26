# Energy Resources Spring Application

This project is a Java Spring Boot application that uses Apache Kafka for the handling of device events and storing their states. The project is composed of several modules which are detailed below:


## Project Structure

The project is structured into the following key components:

- **Controllers**: This is where the REST endpoints are defined. For this application, we have two controllers:
    - `DeviceEventsEndpoint`: Handles the POST requests to publish device events to a Kafka topic.
    - `DeviceStateEndpoint`: Handles the GET requests to fetch the state of a specific device.

- **Models**: Defines the `DeviceState` model which represents the state of a device.

- **Repositories**: Contains the `DeviceStateRepository` that extends `JpaRepository` for database operations related to the device state.

- **Services**: This is where the core business logic of the application resides. It contains:
    - `DeviceStateService`: Service for saving and loading the device state.
    - `RawRecordAvroService`: Service for handling Avro formatted Kafka messages.
    - `KafkaDeviceEventsStreamListenerService`: Service for consuming Kafka messages and transforming them.
    - `RawToJSONParser`: Service for parsing raw Avro data into JSON format.
    - `RawToCanonicalMapper`: Service for mapping raw data to canonical format.
    - `SchemaRegistryService`: Service for registering Avro schemas with the Schema Registry.

- **EnergyResourcesSpringApplication**: This is the main application class that bootstraps the Spring application.

## Application Workflow

This application works in the following way:

1. A device event is sent to the "devicesEvents" Kafka topic.
2. `KafkaDeviceEventsStreamListenerService` consumes the device event from the "devicesEvents" topic.
3. The event data is transformed from raw format to JSON using `RawToJSONParser`.
4. The JSON event is then transformed into a canonical format using `RawToCanonicalMapper`.
5. The canonical event is published to the "devicesCanonicalEvents" Kafka topic.
6. Another stream within `KafkaDeviceEventsStreamListenerService` consumes the canonical event from the "devicesCanonicalEvents" topic.
7. If the event contains a "charging" key, the application updates the device's state in the database using `DeviceStateService`.

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
