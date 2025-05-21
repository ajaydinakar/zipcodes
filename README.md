# zipcodes


## Table of Contents
- [Introduction](#introduction)
- [Purpose](#purpose)
- [Key Features](#key-features)
- [Architecture and Workflow](#architecture-and-workflow)
- [Major Components](#major-components)
  - [Controller](#controller)
  - [Service Layer](#service-layer)
  - [Repository Layer](#repository-layer)
  - [Models](#models)
  - [Redis Configuration](#redis-configuration)
- [Usage](#usage)
- [Summary](#summary)

---

## Introduction

**zipcodes** is a Spring Boot application that provides an API to retrieve location information for a given zipcode. It leverages an external public API for fetching data and uses Redis as a caching layer to optimize repeated requests for the same zipcode. The application demonstrates integration between RESTful services, data caching, and Spring Data repositories.

---

## Purpose

The main purpose of this application is to:
- Expose an API endpoint that returns location data for a given zipcode by querying an external API.
- Cache the fetched location data in Redis to speed up subsequent queries for the same zipcode.
- Demonstrate efficient data retrieval, caching, and management using Spring Boot and Redis.

---

## Key Features

- **REST API**: Provides endpoints to fetch location information by zipcode and country code.
- **External API Integration**: Fetches data from an external public API when the data is not cached.
- **Redis Caching**: Uses Redis to cache location responses to minimize external API calls and improve performance.
- **Spring Data Integration**: Utilizes both JPA and custom repository patterns for Redis operations.
- **Flexible Redis Configuration**: Supports both Jedis and Lettuce Redis clients (with configuration examples).

---

## Architecture and Workflow

1. **API Request**: A client sends a request to the API endpoint with a country code and zipcode.
2. **Cache Lookup**: The application checks Redis to see if the requested data is already cached.
3. **External API Query (if needed)**: If not cached, it fetches the location information from an external API.
4. **Cache Population**: The fetched data is stored in Redis for future requests.
5. **Response**: The location data (either from Redis or the external API) is returned to the client.

---

## Major Components

### Controller

- **LocationController**  
  Handles incoming HTTP requests related to location queries. It delegates the actual processing to the service layer.

### Service Layer

- **LocationService**  
  Uses Spring WebClient to interact with the external API. Checks the repository (Redis) for cached data before querying the API. If data is fetched from the API, it is saved in Redis.

- **LocationServiceRest**  
  Similar to `LocationService` but uses `RestTemplate` for the external API calls and interacts with a custom Redis repository.

### Repository Layer

- **LocationRepository**  
  A standard Spring Data `CrudRepository` for persisting `Location` objects in Redis.

- **LocationHashRepository**  
  A custom repository using Redis Hash operations for more granular cache management.

- **LocationDAO**  
  Acts as a data access object, abstracting interactions between the service layer and the repositories (both JPA and custom Redis repositories).

### Models

- **Location**  
  Represents the location data, including postal code, country, abbreviation, and a list of places.

- **Place**  
  Represents a place within a location, including state, latitude, longitude, etc.

### Redis Configuration

- **RedisConfig**  
  Defines beans for Redis connection factories and templates. Supports configuration of Jedis (and optionally Lettuce) clients, serialization settings, and pooling.

- **RedisConfigurationProperties**  
  Maps Redis connection properties from application configuration (host, port, password, pool settings).

---

## Usage

To use this application:
1. **Start Redis**: Ensure a Redis server is running and reachable by the application.
2. **Configure Application**: Set the Redis connection details and the external API base URL in your configuration file (`application.yaml` or `application.properties`).
3. **Run the Application**: Execute the main method in `ZipcodesApplication.java` to start the Spring Boot app.
4. **API Calls**: Use the provided API endpoint(s) to request location details by country and zipcode. On first request, data is fetched from the external API and cached. Subsequent requests for the same zipcode will be served from Redis, significantly improving response time.

---

## Summary

The **zipcodes** repository is a practical example of integrating a RESTful API with Redis caching in a Spring Boot application. Its architecture separates concerns across controllers, services, and repositories, and demonstrates effective caching strategies using both standard and custom Redis operations. The result is a performant, scalable microservice for zipcode-based location lookups.
