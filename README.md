# Framework comparison 2022

## Summary:
Comparison of simple implementation of REST endpoint using different frameworks.
Without any performance tweaking.
Just what project generators can give you when you start project.

## Compared frameworks:

1. SpringBoot (2.7.3) used: [Spring Initializr](https://start.spring.io/)
2. Quarkus (2.11.3.Final) used: [Quarkus Starter](https://code.quarkus.io/)
3. Micronaut (3.6.1) used: [Micronaut Launch](https://micronaut.io/launch/)
4. Basic pure Netty implementation (Netty 4.1.80.Final)

Java used for compilation and runtime: [Azul Zulu JDK 17.0.2](https://www.azul.com/downloads/?version=java-17-lts&os=windows&architecture=x86-64-bit&package=jdk)

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

<table>
    <thead><tr><th>Label</th><th>Throughput (req/sec)</th><th>Min</th><th>Max</th><th>99% Line</th><th>Avg</th><th>% Perf</th></tr></thead>
    <tbody>
        <tr>
            <td>Netty 1st run</td><td>82,856.906</td><td>0</td><td>478</td><td>2</td>
            <td rowspan=4>83,658.489</td><td rowspan=4>100%</td>
        </tr>
        <tr><td>Netty 2nd run</td><td>83,257.014</td><td>0</td><td>179</td><td>2</td></tr>
        <tr><td>Netty 80MB 1st run</td><td>82,624.143</td><td>0</td><td>338</td><td>2</td></tr>
        <tr><td>Netty 80MB 2nd run</td><td>85,895.894</td><td>0</td><td>125</td><td>2</td></tr>
        <tr>
            <td>Spring 1st run</td><td>51,829.584</td><td>0</td><td>358</td><td>11</td>
            <td rowspan=4>55,905.438</td><td rowspan=4>66.83%</td>
        </tr>
        <tr><td>Spring 2nd run</td><td>61,489.270</td><td>0</td><td>192</td><td>11</td></tr>
        <tr><td>Spring 80MB 1st run</td><td>51,064.699</td><td>0</td><td>327</td><td>14</td></tr>
        <tr><td>Spring 80MB 2nd run</td><td>59,238.197</td><td>0</td><td>128</td><td>6</td></tr>
        <tr>
            <td>Quarkus 1st run</td><td>65,053.344</td><td>0</td><td>619</td><td>21</td>
            <td rowspan=4>72,426.200</td><td rowspan=4>86.57%</td>
        </tr>
        <tr><td>Quarkus 2nd run</td><td>77,184.316</td><td>0</td><td>224</td><td>2</td></tr>
        <tr><td>Quarkus 80MB 1st run</td><td>70,175.439</td><td>0</td><td>857</td><td>10</td></tr>
        <tr><td>Quarkus 80MB 2nd run</td><td>77,291.699</td><td>0</td><td>221</td><td>2</td></tr>
        <tr>
            <td>Micronaut 1st run</td><td>61,709.349</td><td>0</td><td>603</td><td>6</td>
            <td rowspan=4>69,689.849</td><td rowspan=4>83.30%</td>
        </tr>
        <tr><td>Micronaut 2nd run</td><td>78,585.462</td><td>0</td><td>193</td><td>2</td></tr>
        <tr><td>Micronaut 80MB 1st run</td><td>61,387.354</td><td>0</td><td>794</td><td>7</td></tr>
        <tr><td>Micronaut 80MB 2nd run</td><td>77,077.231</td><td>0</td><td>161</td><td>3</td></tr>
    </tbody>
</table>
