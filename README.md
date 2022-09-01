# Framework comparison 2022

## Compared frameworks:

1. SpringBoot (2.7.3)
2. Quarkus (2.11.3.Final)
3. Micronaut (3.6.1)
4. Basic pure Netty implementation (Netty 4.1.80.Final)

Java used for compilation and runtime: Zulu OpenJDK 17.0.2

## Implementation specifics:

1. Implemented REST endpoint /hello/{name}
2. Validation for NotNull for {name}
3. Used service bean factory to create singleton
4. Used Property loading from config file
5. Response is plain text
6. All servers runs default on port 8080
7. Netty app is minimalistic without Singleton service creation.

## How to build:

### SpringBoot application:

Run from spring-example folder: gradlew(.bat) clean build

### Quarkus application:

Run from quarkus-example folder: gradlew(.bat) clean build

### Micronaut application:

Run from micronaut-example folder: gradlew(.bat) clean build

### Netty application:

Run from netty-example folder: gradlew(.bat) clean build :shadowJar

## How to run:

### SpringBoot application:

Run from spring-example folder: java -jar build\libs\spring-example-0.0.1-SNAPSHOT.jar

With memory limited to 80MB: java -Xmx80m -jar build\libs\spring-example-0.0.1-SNAPSHOT.jar

### Quarkus application:

Run from quarkus-example folder: java -jar build\quarkus-app\quarkus-run.jar

With memory limited to 80MB: java -Xmx80m -jar build\quarkus-app\quarkus-run.jar

### Micronaut application:

Run from micronaut-example folder: java -jar build\libs\micronaut-example-0.1-all.jar

With memory limited to 80MB: java -Xmx80m -jar build\libs\micronaut-example-0.1-all.jar

### Netty application:

Run from netty-example folder: java -jar build\libs\netty-example-0.1-all.jar

With memory limited to 80MB: java -Xmx80m -jar build\libs\netty-example-0.1-all.jar

## Some metrics:

Measured on: AMD Ryzen 7 3700X 8-Core Processor, 48 GB RAM

### Build size:

1. SpringBoot app: 20,9 MB
2. Quarkus app: 14,6 MB
3. Micronaut app: 13,5 MB
4. Netty app: 4,37 MB

### Startup:

1. SpringBoot app: 1976 ms
2. Quarkus app: 979 ms
3. Micronaut app: 1056 ms
4. Netty app: 409 ms

### Throughput:

Used JMeter, 100 threads (users) with ramp-up period 1 second, 10 000 requests (1 000 000 requests total)

| **Label** | **Throughput (req/sec)** | **Min** | **Max** | **99% Line** | **Avg** | **% Perf** |
| --- | --- | --- | --- | --- | --- | --- |
| Netty 1st run | 82,856.906 | 0 | 478 | 2 | 83,658.489 | 100% |
| Netty 2nd run | 83,257.014 | 0 | 179 | 2 |
| Netty 80MB 1st run | 82,624.143 | 0 | 338 | 2 |
| Netty 80MB 2nd run | 85,895.894 | 0 | 125 | 2 |
| Spring 1st run | 51,829.584 | 0 | 358 | 11 | 55,905.438 | 66.83% |
| Spring 2nd run | 61,489.270 | 0 | 192 | 11 |
| Spring 80MB 1st run | 51,064.699 | 0 | 327 | 14 |
| Spring 80MB 2nd run | 59,238.197 | 0 | 128 | 6 |
| Quarkus 1st run | 65,053.344 | 0 | 619 | 21 | 72,426.200 | 86.57% |
| Quarkus 2nd run | 77,184.316 | 0 | 224 | 2 |
| Quarkus 80MB 1st run | 70,175.439 | 0 | 857 | 10 |
| Quarkus 80MB 2nd run | 77,291.699 | 0 | 221 | 2 |
| Micronaut 1st run | 61,709.349 | 0 | 603 | 6 | 69,689.849 | 83.30% |
| Micronaut 2nd run | 78,585.462 | 0 | 193 | 2 |
| Micronaut 80MB 1st run | 61,387.354 | 0 | 794 | 7 |
| Micronaut 80MB 2nd run | 77,077.231 | 0 | 161 | 3 |