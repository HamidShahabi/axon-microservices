# Axon Microservices Project

This repository contains a distributed system implemented with microservices, utilizing the **Axon Framework** for CQRS and Event Sourcing patterns. The system consists of several microservices, each handling specific domains such as **Order**, **Payment**, and **Discount**, and communicates through events.

## Table of Contents

1. [Overview](#overview)
2. [Architecture](#architecture)
3. [Microservices](#microservices)
4. [Technologies Used](#technologies-used)
5. [Setup Instructions](#setup-instructions)
6. [Docker Deployment](#docker-deployment)
7. [Kubernetes Deployment](#kubernetes-deployment)
8. [Contributing](#contributing)
9. [License](#license)

---

## Overview

This project demonstrates a distributed microservices architecture built using:
- **Axon Framework**: For event sourcing, CQRS, and sagas.
- **Spring Boot**: For REST APIs and service orchestration.
- **PostgreSQL**: For persistent storage.

The system is designed to:
- Process orders with discounts and payments.
- Maintain eventual consistency between services using sagas.
- Persist event streams for replays.

---

## Architecture

![Architecture Diagram](https://via.placeholder.com/800x400?text=Architecture+Diagram)

1. **Order Service**:
   - Manages order creation, confirmation, and cancellation.
   - Initiates sagas for coordinating payments and discounts.

2. **Payment Service**:
   - Processes payments and notifies the result.

3. **Discount Service**:
   - Manages discount codes and their availability.

### Key Concepts
- **Event Sourcing**: All state changes are captured as a sequence of events.
- **CQRS**: Queries and commands are handled separately for scalability.
- **Saga Orchestration**: Long-running business processes are coordinated across microservices.

---

## Microservices

### **1. Order Service**
- **Responsibilities**:
  - Create orders.
  - Manage the lifecycle of orders.
  - Orchestrate sagas for payment and discount validation.
- **Endpoints**:
  - `POST /orders` - Create a new order.
  - `GET /orders/{id}` - Retrieve order details.

### **2. Payment Service**
- **Responsibilities**:
  - Process payments.
  - Notify the order service of the result.
- **Endpoints**:
  - `POST /payments` - Process a payment request.

### **3. Discount Service**
- **Responsibilities**:
  - Manage discount codes.
  - Reserve and release discounts during order processing.
- **Endpoints**:
  - `POST /discounts` - Create a discount.
  - `GET /discounts/{code}` - Retrieve discount details.

---

## Technologies Used

- **Frameworks**:
  - Axon Framework
  - Spring Boot

- **Databases**:
  - PostgreSQL

- **Build Tools**:
  - Maven

- **Containerization**:
  - Docker
  - Kubernetes

- **Monitoring**:
  - Actuator Endpoints

---

## Setup Instructions

### **Prerequisites**
- **Java 17**
- **Maven**
- **Docker** (optional for containerized setup)
- **PostgreSQL**

### **Steps to Run Locally**

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-repo/axon-microservices.git
   cd axon-microservices
   ```

2. **Build the Project**:
   ```bash
   mvn clean install
   ```

3. **Run Microservices**:
   - Navigate to each service directory and start the application:
     ```bash
     cd order-service
     mvn spring-boot:run
     ```
   Repeat for `discount-service` and `payment-service`.

4. **Access APIs**:
   - **Order Service**: `http://localhost:8081`
   - **Discount Service**: `http://localhost:8082`
   - **Payment Service**: `http://localhost:8083`

---

## Docker Deployment

### **Steps to Deploy with Docker Compose**

1. **Build Images**:
   ```bash
   docker-compose build
   ```

2. **Start Services**:
   ```bash
   docker-compose up -d
   ```

3. **Verify Running Services**:
   ```bash
   docker-compose ps
   ```

4. **Access APIs**:
   - Use the same ports as the local setup (e.g., `http://localhost:8081` for the Order Service).

---

## Kubernetes Deployment

1. **Create a Cluster**:
   If using Minikube:
   ```bash
   minikube start
   ```

2. **Apply Kubernetes Resources**:
   Deploy all services and databases:
   ```bash
   kubectl apply -f k8s/
   ```

3. **Verify Deployments**:
   ```bash
   kubectl get pods
   kubectl get services
   ```

4. **Access Services**:
   - Use `kubectl port-forward` to access services locally.
   - Example:
     ```bash
     kubectl port-forward service/order-service 8081:8081
     ```

---

## Contributing

We welcome contributions! Please follow these steps:
1. Fork the repository.
2. Create a new branch for your feature/fix.
3. Commit your changes.
4. Open a pull request.

---

## License

This project is licensed under the Apache 2.0 License. See the `LICENSE` file for details.

