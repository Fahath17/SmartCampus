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

# Report

---

## Question and Answers

---

## 01. In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.

**Answer:**

In JAX-RS, the default lifecycle of a resource class is transient, that is, a new resource class instance is instantiated with each incoming call to the HTTP method. Resource classes are not considered singletons by default in the runtime. This implies that every request receives a unique instance of the resource class and every instance is not dependent on the other.

### Effects on In-Memory Data Structures:
- This means that each request will be allocated a new instance of the resource class and as a result does not share the state or data with that of the other requests.
- Nevertheless, when the resource class has access to in-memory data structures, which are shared (a map or list), then the data structures should be thread-safe to avoid losing data or race conditions. To illustrate, to safely support multiple requests using concurrent access, it is possible to use ConcurrentHashMap or synchronized collections.

### To be synchronized and prevent race conditions:
- The data to be shared must be handled in a thread-safe way e.g. with synchronized blocks or with concurrent collections such as ConcurrentHashMap provided by Java.
- Avoiding Global State: Every request is a new instance of a resource, so it is advisable to store shared data outside the resource class, e.g. by using Singleton beans or database storage.

**Conclusion:**  
To manage the resources effectively, the in-memory data structures must be thread-safe to support multiple requests at a time, and you should not store data unique to a single instance of a resource class.

---

## 02. Why is the provision of ”Hypermedia” (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?

**Answer:**

Hypermedia As the Engine of Application State (HATEOAS) is a core concept of RESTful systems, which gives clients navigable links that they can see in the response that enable the clients to discover dynamically what actions and transitions can occur and what they are capable of doing without external documentation.

### Benefits of HATEOAS:
- Dynamic Interaction: Clients are able to dynamically interact with the API. Clients need not hardcode endpoint URLs when using an application; they can use links in the responses to browse the app.
- Minimized Documentation Reliance: Clients do not require outside documentation as a reference to endpoint information with HATEOAS. The links to potential subsequent actions are also included in the API response itself, which helps minimize the maintenance cost of documentation and makes the client always use the right URLs.
- Flexibility: HATEOAS enables APIs to change, without client-breaking. As clients learn what they can do using the API itself, there is no longer the need to change client code as the API changes.

### Benefits to Clients:
- Clients can discover available actions dynamically.
- Reduces errors during API changes since clients rely on provided links instead of hardcoded paths.

---

## 03. When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing.

**Answer:**

### Returning Only IDs:
**Advantages:**
- Reduces the network bandwidth since the size of response is less.
- Only identifiers are sent, reducing data transfer load.

**Disadvantages:**
- Clients need additional requests to fetch full details, increasing HTTP calls.

### Returning Full Room Objects:
**Advantages:**
- Provides all data in a single response.
- Reduces the need for multiple API calls.

**Disadvantages:**
- Larger response size, which may impact performance and bandwidth.

### Best Approach:
In large datasets or bandwidth-sensitive environments, returning IDs is preferable. Returning full objects is better when clients need complete data immediately.

---

## 04. Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.

**Answer:**

Yes, the DELETE operation is idempotent.

### Explanation:
- An operation is idempotent when multiple identical requests produce the same result as a single request.
- The first DELETE request removes the resource.
- Subsequent DELETE requests will return **404 Not Found** because the resource no longer exists.

**Conclusion:**  
Even if the DELETE request is repeated, the final state remains unchanged, confirming idempotency.

---

## 05. We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?

**Answer:**

The `@Consumes` annotation specifies the media type the resource method can process.

### Consequences of Mismatch:
- If a client sends data in formats like `text/plain` or `application/xml`, JAX-RS cannot process it.

### JAX-RS Behavior:
- The runtime attempts to match the request's Content-Type with the supported type.
- If no match is found, it returns:

**HTTP 415 – Unsupported Media Type**

**Conclusion:**  
Clients must send data in JSON format to be successfully processed.

---

## 06. You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/v1/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?

**Answer:**

### Advantages of Query Parameters:
- **Separation of Concerns:** Path defines the resource, query parameters define filtering.
- **Scalability:** Easily extendable (e.g., `?status=active&type=CO2`)
- **Multiple Filters:** Supports combining filters easily.
- **Cleaner URLs:** Keeps API design more intuitive and RESTful.

### Path-Based Filtering Issues:
- Harder to extend
- Leads to complex URL structures

**Conclusion:**  
Query parameters provide better flexibility, scalability, and clarity.

---

## 07. Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class?

**Answer:**

### Benefits:
- **Modularity:** Each resource handled in separate classes.
- **Separation of Concerns:** Cleaner and more focused code.
- **Scalability:** Easier to expand API with new sub-resources.

### Without Sub-Resource Locators:
- Leads to large, complex, and hard-to-maintain classes.

**Conclusion:**  
Delegating logic improves maintainability and organization of large APIs.

---

## 08. Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?

**Answer:**

- **404 Not Found:** Indicates the resource itself does not exist.
- **422 Unprocessable Entity:** Indicates the request is valid but contains semantic errors.

### Example:
- Missing reference inside JSON → **422 is appropriate**

**Conclusion:**  
422 more accurately reflects issues within request data rather than resource absence.

---

## 09. From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?

**Answer:**

### Risks:
- Exposure of internal class names and methods
- Reveals frameworks and libraries used
- Provides insight into system structure

### Attacker Advantages:
- Identify vulnerabilities
- Plan targeted attacks

### Best Practice:
- Return generic error messages (e.g., 500 Internal Server Error)
- Log detailed stack traces internally

---

## 10. Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?

**Answer:**

### Benefits:
- **Centralized Logic:** Logging handled in one place
- **Consistency:** Uniform logging across the application
- **Separation of Concerns:** Keeps business logic clean
- **Flexibility:** Apply globally or conditionally

**Conclusion:**  
Filters improve maintainability, readability, and consistency of logging.

---