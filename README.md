# Smart Campus Web API

## Application Overview

The **Smart Campus Web API** is a backend web application designed to manage a university’s smart infrastructure.

It provides a RESTful interface that allows systems or users to:

* Create and manage campus rooms
* Register and monitor sensors inside rooms
* Store and retrieve sensor readings in real time

This application simulates a real-world **campus automation system**, where devices continuously send data to a central server.

---

## Key Features

### Room Management

* Create rooms with capacity and name
* View all rooms or a specific room
* Prevent deletion if sensors are still assigned

---

### Sensor Management

* Register sensors (e.g., Temperature, CO2)
* Link sensors to rooms
* Filter sensors by type

---

### Sensor Readings

* Store historical readings for each sensor
* Retrieve full reading history
* Automatically update current sensor value

---

### Error Handling

* Prevent invalid operations (e.g., deleting active rooms)
* Return proper HTTP status codes
* Provide clear JSON error messages

---

### Logging System

* Logs every API request
* Logs response status
* Helps monitor system activity

---

## System Architecture

This application follows a **RESTful client-server architecture**:

* Client → Sends HTTP requests
* Server → Processes and returns JSON responses

### Data Storage

* Uses **in-memory storage (HashMaps & Lists)**
* No database is used (as per coursework requirements)

---

## Technologies Used

* Java (JDK 17+)
* Maven
* JAX-RS (Jersey)
* Grizzly HTTP Server
* JSON (Jackson)

---

## Running the Web Application

### 1. Clone the Repository

```bash
git clone https://github.com/Fahath17/SmartCampus.git
cd SmartCampus
```

---

### 2. Build the Project

```bash
mvn clean install
```

---

### 3. Start the Server

Run `Main.java` from your IDE
OR:

```bash
mvn exec:java
```

---

### 4. Open the Application

Access the API via browser or Postman:

```bash
http://localhost:8080/SmartCampus/api/v1
```

---

## API Structure

### Root Endpoint (Entry Point)

```bash
GET /api/v1
```

Returns:

* API version
* Available resources
* Navigation links

---

## Available Endpoints

### Rooms

| Method | Endpoint    | Description   |
| ------ | ----------- | ------------- |
| GET    | /rooms      | Get all rooms |
| POST   | /rooms      | Create room   |
| GET    | /rooms/{id} | Get room      |
| DELETE | /rooms/{id} | Delete room   |

---

### Sensors

| Method | Endpoint          | Description     |
| ------ | ----------------- | --------------- |
| POST   | /sensors          | Create sensor   |
| GET    | /sensors          | Get all sensors |
| GET    | /sensors?type=CO2 | Filter sensors  |

---

### Sensor Readings

| Method | Endpoint               | Description  |
| ------ | ---------------------- | ------------ |
| GET    | /sensors/{id}/readings | Get readings |
| POST   | /sensors/{id}/readings | Add reading  |

---

## Example API Usage

### Create a Room

```bash
curl -X POST http://localhost:8080/SmartCampus/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"R1","name":"Library","capacity":100}'
```

---

### Create a Sensor

```bash
curl -X POST http://localhost:8080/SmartCampus/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"S1","type":"CO2","status":"ACTIVE","roomId":"R1"}'
```

---

### Add Sensor Reading

```bash
curl -X POST http://localhost:8080/SmartCampus/api/v1/sensors/S1/readings \
-H "Content-Type: application/json" \
-d '{"id":"RD1","timestamp":1710000000,"value":25.5}'
```

---

### Filter Sensors

```bash
curl http://localhost:8080/SmartCampus/api/v1/sensors?type=CO2
```

---

## Error Handling Design

The application ensures safe and predictable behavior:

| Situation                  | Response                  |
| -------------------------- | ------------------------- |
| Deleting room with sensors | 409 Conflict              |
| Invalid room reference     | 422 Unprocessable Entity  |
| Sensor unavailable         | 403 Forbidden             |
| Unexpected errors          | 500 Internal Server Error |

---

## Design Decisions

### In-Memory Storage

Used for simplicity and coursework requirement
(No database allowed)

---

### RESTful Design

* Uses proper HTTP methods
* Clean resource-based URLs
* Supports filtering and nesting

---

### Sub-Resource Pattern

Used for managing sensor readings:

```bash
/sensors/{id}/readings
```

This keeps the system modular and scalable.

---

## Coursework Report Answers

### Resource Lifecycle

JAX-RS creates a new instance per request.
This prevents shared state issues and improves safety.

---

### HATEOAS

Helps clients navigate API dynamically using links.
Removes need for hardcoded endpoints.

---

### DELETE Idempotency

DELETE is idempotent because repeated calls produce the same result.

---

### Query Parameters

Used for filtering because they are optional and flexible.

---

### HTTP 422 vs 404

422 is better when request format is correct but data is invalid.

---

### Security Risk of Stack Traces

Stack traces expose internal details like:

* File paths
* Class names
* System structure

---

### Logging via Filters

Filters allow centralized logging instead of repeating code.

---
