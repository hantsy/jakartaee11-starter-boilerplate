# Jakarta EE 11 Starter Boilerplate

[![build](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/build.yml/badge.svg)](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/build.yml)
[![arq-glassfish-managed](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/arq-glassfish-managed.yml/badge.svg)](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/arq-glassfish-managed.yml)
[![arq-payara-managed](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/arq-payara-managed.yml/badge.svg)](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/arq-payara-managed.yml)
[![arq-wildfly-managed](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/arq-wildfly-managed.yml/badge.svg)](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/arq-wildfly-managed.yml)
[![arq-liberty-managed](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/arq-liberty-managed.yml/badge.svg)](https://github.com/hantsy/jakartaee11-starter-boilerplate/actions/workflows/arq-liberty-managed.yml)

This project provides a starter boilerplate for Jakarta EE 11 applications, supporting various application servers and integration tests written in [Arquillian testing framework](https://arquillain.org).

## Prerequisites

* **Java 21**
* **Maven 3.9+** (Maven 4 is recommended)

## Build and Run

You can build and run the application with different application servers using Maven profiles:

* **GlassFish**:
  ```bash
  mvn clean package -Pglassfish
  ```
* **GlassFish Embedded**:
  ```bash
  mvn clean package -Pglassfish-embedded
  ```
* **Payara**:
  ```bash
  mvn clean package -Ppayara
  ```
* **Open Liberty**:
  ```bash
  mvn clean package -Popenliberty
  ```
* **WildFly**:
  ```bash
  mvn clean package -Pwildfly
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
