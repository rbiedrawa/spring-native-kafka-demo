# Spring Kafka - GraalVM native executable demo

This repository demonstrates how to transform a `Spring Boot Kafka` application to native executable using GraalVM.

## Getting Started

### Prerequisite

* Docker

### Usage

#### 1. Create native image of demo application

```shell
./gradlew bootBuildImage
```

*** *Occasionally You may see the following error: `Error: Image building with exit status 137`.This error occurs if the
system runs out of ram, in order to fix this increase Docker memory e.g 10GB*

#### 2. Start Docker containers

```shell
cd docker
docker compose up -d
```

#### 3. Check if all containers are up and running

```shell
docker compose ps

# NAME                 SERVICE              STATUS              PORTS
# akhq                 akhq                 running             0.0.0.0:8080->8080/tcp, :::8080->8080/tcp
# kafka                kafka                running             0.0.0.0:9092->9092/tcp, :::9092->9092/tcp, 0.0.0.0:9101->9101/tcp, :::9101->9101/tcp
# schema-registry      schema-registry      running             0.0.0.0:8081->8081/tcp, :::8081->8081/tcp
# spring-native-demo   spring-native-demo   running             0.0.0.0:7777->7777/tcp, :::7777->7777/tcp
# zookeeper            zookeeper            running             0.0.0.0:2181->2181/tcp, :::2181->2181/tcp, 2888/tcp, 3888/tcp
```

#### 4. Verify that messages are produced/consumed correctly.

```shell
docker compose logs -f spring-native-demo

# spring-native-demo  | 2021-07-07 17:46:28.155  INFO 1 --- [   scheduling-1] c.r.app.customers.RandomEventGenerator   : Successfully sent CustomerCreated event {"eid": "5178e6bf-c241-45bc-9e1b-90a789ec328c", "userId": "9958e080-bafc-4f84-b4c0-f1ee180a3db3", "email": "2a22ea77-6187-4860-98de-a14a549ab06a@test.com", "name": "Name adb925cc-aa8e-4be1-be21-7f71c0d0aa0a"}
# spring-native-demo  | 2021-07-07 17:46:28.158  INFO 1 --- [-StreamThread-1] c.r.app.customers.CustomerEventsKStream  : Consumed customer event {"eid": "5178e6bf-c241-45bc-9e1b-90a789ec328c", "userId": "9958e080-bafc-4f84-b4c0-f1ee180a3db3", "email": "2a22ea77-6187-4860-98de-a14a549ab06a@test.com", "name": "Name adb925cc-aa8e-4be1-be21-7f71c0d0aa0a"}.
# spring-native-demo  | 2021-07-07 17:46:28.158  INFO 1 --- [ntainer#0-0-C-1] c.r.app.customers.CustomerEventListener  : Received CustomerCreated event -> {"eid": "5178e6bf-c241-45bc-9e1b-90a789ec328c", "userId": "9958e080-bafc-4f84-b4c0-f1ee180a3db3", "email": "2a22ea77-6187-4860-98de-a14a549ab06a@test.com", "name": "Name adb925cc-aa8e-4be1-be21-7f71c0d0aa0a"}
```

#### 5. Stop demo application

```shell
docker compose stop spring-native-demo
```

#### 6. Start demo application

```shell
docker compose start spring-native-demo
```

#### 7. Investigate application startup time

```shell
docker compose logs spring-native-demo | grep "Started NativeApplication"

# spring-native-demo  | 2021-07-07 18:02:34.108  INFO 1 --- [           main] com.rbiedrawa.app.NativeApplication      : Started NativeApplication in 0.152 seconds (JVM running for 0.156)
# spring-native-demo  | 2021-07-07 18:03:35.373  INFO 1 --- [           main] com.rbiedrawa.app.NativeApplication      : Started NativeApplication in 0.127 seconds (JVM running for 0.13)
```

#### 8. Destroy demo infrastructure

When you're done, stop Docker containers by running.

```shell
docker compose down -v
```

## Important Endpoints

| Name | Endpoint | 
| -------------:|:--------:|
| `Akhq UI` | [http://localhost:8080/](http://localhost:8080/) |
| `Schema-registry` | [http://localhost:8081/](http://localhost:8081/) |

## References

For further reference, please consider the following sections:

* [Spring Native Reference Guide](https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/)
* [Configure the Spring AOT Plugin](https://docs.spring.io/spring-native/docs/0.10.1/reference/htmlsingle/#spring-aot-gradle)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/2.5.2/reference/htmlsingle/#boot-features-kafka)
* [Kafka Docker Images](https://github.com/confluentinc/kafka-images)

## License

Distributed under the MIT License. See `LICENSE` for more information.
