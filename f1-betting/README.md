# F1 Betting Service

A Spring Boot application that simulates betting on Formula 1 events.  
It provides APIs to list events, place bets, record event outcomes, and check user balances.  
The system enforces betting rules, validates requests, and ensures fair balance updates.

---

## Overview

- Built with **Spring Boot (Java 17)** and **Spring Data JPA**.
- Uses an **H2 in-memory database** (resets on restart).
- Event data is provided via a pluggable **F1Provider** abstraction.
- Includes a **stub provider** with sample event(s) and random odds.
- Designed with **layered architecture**: controllers → services → repositories → domain.
- Includes **unit tests** and **integration tests**.

---

## Core Features

- **List events**: filterable by session type, year, and country.
- **Place bets**: deducts user balance and creates a bet.
- **Record outcomes**: admin marks winning driver; balances updated.
- **Check balances**: query user balances directly.
- **Request validation**: payloads validated with `@Valid` DTOs.
- **Error handling**: centralized via `GlobalExceptionHandler`.
- **Integrity rules**:
    - Each user starts with **€100**.
    - **No duplicate bets** allowed (same user, event, driver).
    - **No bets allowed** after event outcome is recorded.

---

## Technology Stack

- **Spring Boot 3.2.2**
- **Spring Data JPA** (Hibernate)
- **H2 Database** (in-memory, dev mode)
- **JUnit 5** + **Mockito** for unit testing
- **Spring Boot Test** + **MockMvc** for integration testing
- **Maven** for build and dependency management

---

## API Endpoints

- GET /api/events
- POST /api/bets
- GET /api/users/{id}/balance

---

## Tests

The project includes both unit and integration tests.
- **Unit Tests (BettingServiceTest)**:
    - Place bet success.
    - Insufficient balance.
    - Process outcome (winning + losing bets).
    - Duplicate bet rejection.
    - Bet after outcome rejection.
- **Integration Tests (BettingControllerIntegrationTest)**:
      - Place bet via REST (200 OK).
      - Duplicate bet rejected (400 Bad Request).

---