# Cinema Ticket SOAP

A Spring Boot SOAP-based cinema ticket reservation system with a simple Thymeleaf web UI and SQLite persistence.

## Features

- SOAP service for movie and reservation workflows
- SOAP header authentication (`admin` / `1234`)
- Seat reservation with duplicate-seat protection
- Reservation lookup and cancellation
- Web UI for browsing movies/showtimes, reserving seats, searching reservations, and cancelling reservations
- SQLite database via Spring Data JPA

## SOAP Operations

The `TicketService` SOAP API exposes:

- `GetMovies()`
- `GetShowTimes(movieId)`
- `ReserveSeat(movieId, showTimeId, seatNumber, customerName)`
- `GetReservation(reservationId)`
- `CancelReservation(reservationId)`

WSDL is available at:

- `http://localhost:8080/soap-api/ticket?wsdl`

SOAP endpoint:

- `http://localhost:8080/soap-api/ticket`

## Authentication (SOAP Header)

Each SOAP request must include this header:

```xml
<soapenv:Header xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
  <auth:AuthenticationHeader xmlns:auth="http://auth.cinema.org/">
    <auth:username>admin</auth:username>
    <auth:password>1234</auth:password>
  </auth:AuthenticationHeader>
</soapenv:Header>
```

If the header is missing/invalid, the service returns a SOAP fault.

## Tech Stack

- Java 17
- Spring Boot 4.0.6
- Apache CXF (JAX-WS)
- Spring MVC + Thymeleaf
- Spring Data JPA
- SQLite (`cinema.db`)
- Maven Wrapper (`mvnw`)

## Project Structure

- `src/main/java/.../service` - SOAP contract and implementation
- `src/main/java/.../config` - CXF endpoint config + SOAP auth handler
- `src/main/java/.../controller` - Web UI controller
- `src/main/java/.../model` - JAXB/JPA entities and SOAP response models
- `src/main/java/.../repository` - Spring Data repositories
- `src/main/resources/templates` - Thymeleaf pages
- `src/main/resources/application.yaml` - CXF + datasource config
- `src/test/xml-test/cinema-ticket-soapui-project.xml` - ready SoapUI project

## Prerequisites

- Java 17+
- Maven (optional, wrapper is included)

## Run Locally

```bash
./mvnw spring-boot:run
```

App URLs:

- Web UI: `http://localhost:8080/`
- WSDL: `http://localhost:8080/soap-api/ticket?wsdl`
- SOAP endpoint: `http://localhost:8080/soap-api/ticket`

## Web UI Flow

1. Open `/` and view movie list
2. Open show times for a movie
3. Reserve a seat
4. Search reservation by ID
5. Cancel reservation if needed

## Database

- Database file: `cinema.db` (project root)
- JPA schema update: `spring.jpa.hibernate.ddl-auto=update`
- Tables used: `movie`, `show_time`, `reservation`
- Current repo database includes sample movies/showtimes

> Note: `MockDatabase` exists in the codebase but the running app uses SQLite repositories.

## Test
For SOAP testing:

1. Start the app
2. Import `src/test/xml-test/cinema-ticket-soapui-project.xml` into SoapUI
3. Run the prepared requests
