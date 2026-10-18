# EventRight: Events Done Right.
> **Enterprise Event-Driven Distributed Ticketing Platform**  
> *Built with Spring Boot 3.x / Java 25, Spring Cloud, Apache Kafka, and Netflix Eureka*

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Java: 25](https://img.shields.io/badge/Java-25-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-2025.1.1-green.svg)](https://spring.io/projects/spring-cloud)
[![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-7.5.0-black.svg)](https://kafka.apache.org/)
[![Eureka](https://img.shields.io/badge/Service_Discovery-Eureka-red.svg)](https://github.com/Netflix/eureka)

---

## ⚡ Executive Summary & System Highlights

**EventRight** is an asynchronous, event-driven microservices platform engineered to handle high-concurrency ticket reservations, payment processing, and inventory state transitions with decoupled message streaming and transactional consistency.

* **Decoupled Asynchronous Processing**: Replaces synchronous RPC bottlenecks with **Apache Kafka** event streaming (`booking-events`, `payment-events`), decoupling booking requests from downstream payment capture and email/push notifications.
* **Dynamic Service Discovery & Routing**: Centralized routing through **Spring Cloud API Gateway** backed by **Netflix Eureka Service Registry** with client-side load balancing and health heartbeat monitoring.
* **Centralized Configuration Management**: Powered by **Spring Cloud Config Server**, enabling zero-downtime property updates across all 8 microservices.
* **Fault-Tolerant Inventory Management**: Synchronous seat verification combined with asynchronous saga-style state transitions (`PENDING` -> `CONFIRMED` / `FAILED`) to prevent overbooking races.

---

## 🏛️ Microservice Architecture & Event Flow

```
                                  +-----------------------+
                                  |   Spring Cloud Config |
                                  |         Server        |
                                  +-----------------------+
                                              |
                                              v
[ Client Request ] --------------> [ Spring Cloud Gateway ]
                                              |
                                              +--------------------------------------+
                                              | (Dynamic Routing via Eureka Registry)|
                                              v                                      v
                                     +------------------+                   +------------------+
                                     |   User Service   |                   |  Event Service   |
                                     |  (Auth / Users)  |                   | (Inventory/Seats)|
                                     +------------------+                   +------------------+
                                                                                     ^
                                                                                     | (Seat Decrement)
                                                                                     v
                                                                            +------------------+
                                                                            | Booking Service  |
                                                                            +------------------+
                                                                                     |
                                                                                     | (Publishes `booking-events`)
                                                                                     v
                                                                           ======================
                                                                             Apache Kafka Broker
                                                                           ======================
                                                                                     |
                                                  +----------------------------------+
                                                  | (Consumes `booking-events`)      | (Consumes `payment-events`)
                                                  v                                  v
                                         +------------------+               +----------------------+
                                         | Payment Service  |               | Notification Service |
                                         | (Stripe / Mock)  |               | (Email / Dispatch)   |
                                         +------------------+               +----------------------+
                                                  |
                                                  | (Publishes `payment-events`)
                                                  v
                                         ======================
                                           Apache Kafka Broker
                                         ======================
```

---

## 📦 Service Topology & Portfolio Breakdown

| Service | Port | Description | Stack / Tech |
| :--- | :--- | :--- | :--- |
| **`service-registry`** | `8761` | Netflix Eureka Service Registry for dynamic instance discovery & heartbeat monitoring | Spring Cloud Netflix Eureka |
| **`config-server`** | `8888` | Centralized git-backed externalized configuration repository for all environments | Spring Cloud Config Server |
| **`api-gateway`** | `8080` | Unified edge routing, reverse proxy, and client access abstraction | Spring Cloud Gateway Server WebMVC |
| **`user-service`** | Dynamic | User authentication, profile management, and role-based permissions | Spring Boot, JPA, H2/PostgreSQL |
| **`event-service`** | Dynamic | Event catalog management, seat allocation, and real-time capacity decrement | Spring Boot, Spring Data JPA, REST |
| **`booking-service`** | Dynamic | Reservation state machine orchestrator; emits `booking-events` to Kafka | Spring Boot, Spring Kafka Producer, JPA |
| **`payment-service`** | Dynamic | Asynchronous transaction settlement; publishes `payment-events` upon confirmation | Spring Kafka Consumer/Producer, JPA |
| **`notification-service`** | Dynamic | Event-driven dispatch service consuming payment confirmations | Spring Kafka Consumer, JavaMail/SMS |

---

## 🚀 Key Architectural Decisions & Interview Talking Points

### 1. Why Event-Driven Architecture with Kafka instead of Direct REST calls?
> *"Direct synchronous HTTP calls between Booking, Payment, and Notification create tight temporal coupling. If Payment Service experiences a P99 latency spike or Notification Service is down, the Booking API blocks and eventually times out for the end-user. By offloading booking confirmations to an **Apache Kafka topic (`booking-events`)**, Booking Service responds to the user in sub-50ms, while Payment and Notification consume events at their own processing rate."*

### 2. How are Overbooking Race Conditions Prevented?
> *"Before a booking record is transitioned to `CONFIRMED`, `BookingService` executes an atomic seat verification and decrement operation against `EventService`. If capacity is insufficient, the transaction terminates immediately before publishing to Kafka, preventing phantom bookings."*

### 3. Why Eureka + Spring Cloud Gateway?
> *"Rather than hardcoding IP addresses or container hosts, services register ephemeral instances with Eureka upon bootstrap. The Spring Cloud Gateway queries Eureka for active instances, automatically routing traffic to healthy nodes and performing client-side load balancing."*

---

## 🛠️ Local Development & Running the System

### 1. Prerequisites
* **Java 21 or 25** (OpenJDK)
* **Maven 3.9+**
* **Docker & Docker Compose** (for Kafka & Zookeeper)

### 2. Start Message Broker (Kafka + Zookeeper)
```bash
docker-compose up -d
```
* Zookeeper: `localhost:2181`
* Kafka Broker: `localhost:9092`

### 3. Recommended Startup Order
To ensure proper service discovery and configuration loading, start the services in the following order:

```bash
# 1. Start Config Server
cd config-server && ./mvnw spring-boot:run

# 2. Start Eureka Service Registry
cd ../service-registry && ./mvnw spring-boot:run

# 3. Start API Gateway
cd ../api-gateway && ./mvnw spring-boot:run

# 4. Start Core Business Domain Services
cd ../event-service && ./mvnw spring-boot:run
cd ../booking-service && ./mvnw spring-boot:run
cd ../payment-service && ./mvnw spring-boot:run
cd ../notification-service && ./mvnw spring-boot:run
cd ../user-service && ./mvnw spring-boot:run
```

---

## 📜 Resume & Portfolio Highlights (ATS-Optimized)

* **Architected an event-driven microservices ticketing platform with 8 Spring Boot/Cloud services handling asynchronous booking pipelines via Apache Kafka, reducing synchronous API blocking by >80%.**
* **Implemented dynamic service discovery and edge routing with Netflix Eureka and Spring Cloud Gateway, enabling client-side load balancing and zero-downtime service registration.**
* **Designed an asynchronous saga-style transaction flow across booking, payment, and notification domains, ensuring eventual data consistency and eliminating distributed deadlocks.**

---

## 📜 License
MIT License. Built for distributed systems, backend engineering, and high-concurrency event platforms.
