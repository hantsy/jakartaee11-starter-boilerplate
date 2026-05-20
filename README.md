# Jakarta EE 11 Starter Boilerplate

[![build](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/build.yml/badge.svg)](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/build.yml)
[![arq-glassfish-managed](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/arq-glassfish-managed.yml/badge.svg)](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/arq-glassfish-managed.yml)
[![arq-payara-managed](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/arq-payara-managed.yml/badge.svg)](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/arq-payara-managed.yml)
[![arq-wildfly-managed](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/arq-wildfly-managed.yml/badge.svg)](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/arq-wildfly-managed.yml)
[![arq-liberty-managed](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/arq-liberty-managed.yml/badge.svg)](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/arq-liberty-managed.yml)

This project provides a starter boilerplate for building Jakarta EE 11 applications. It includes support for various application servers and example integration tests written using the [Arquillian testing framework](https://arquillain.org).

## Prerequisites

* **Java 21**
* **Maven 3.9+** (Maven 4 is recommended)

## Technical Compatibility & Issues

* __Payara (Jakarta REST)__: Serialization fails for Java 8 DateTime types because the Jackson v2 stack lacks the necessary `jackson-datatype-jsr310` module. Supplemental testing has also identified various regressions in data handling.

* __WildFly 40.0.0.Beta1__: Initial support for derived query-by-method-name in Jakarta Data has been removed in this version.

* __OpenLiberty 26.0.0.5-beta__: While all test cases pass in isolation, the runtime fails to resolve Jakarta Data repository interfaces during application startup. A fix is reportedly available in the nightly builds; verification is pending.

## Build and Run

You can build and run the application with different application servers using Maven profiles:

* **GlassFish** via [Cargo Maven Plugin](https://codehaus-cargo.github.io/cargo/GlassFish+8.x.html):
  ```bash
  mvn clean package cargo:run -Pglassfish
  ```
* **GlassFish Embedded** via [GlassFish Embedded Maven Plugin](https://github.com/eclipse-ee4j/glassfish-maven-embedded-plugin):
  ```bash
  mvn clean embedded-glassfish:run -Pglassfish-embedded
  ```
* **Payara**:
  ```bash
  mvn clean package cargo:run -Ppayara
  ```
* **Open Liberty**:
  ```bash
  mvn clean package liberty:dev -Popenliberty
  ```
* **WildFly**:
  ```bash
  mvn clean wildfly:run -Pwildfly
  ```

## Running Arquillian Tests

The project includes Arquillian integration tests for various managed containers. Use the following profiles to run them:

* **GlassFish Managed**:
  ```bash
  mvn clean verify -Parq-glassfish-managed
  ```
* **Payara Managed**:
  ```bash
  mvn clean verify -Parq-payara-managed
  ```
* **WildFly Managed**:
  ```bash
  mvn clean verify -Parq-wildfly-managed
  ```
* **Open Liberty Managed**:
  ```bash
  mvn clean verify -Parq-liberty-managed
  ```
