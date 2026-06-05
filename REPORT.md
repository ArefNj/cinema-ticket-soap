# Cinema Ticket SOAP Project Report

## 1) Architecture

This project is implemented as a SOAP web service using:
- Java 17
- Spring Boot
- Apache CXF (JAX-WS)
- SQLite database

Main layers:
- **SOAP Contract Layer**: `TicketService` interface defines SOAP operations.
- **Service Layer**: `TicketServiceImpl` implements business logic and validation.
- **Persistence Layer**: `TicketRepository` performs SQL operations over SQLite.
- **Configuration Layer**:
  - `CxfConfig` publishes endpoint at `/soap-api/ticket`.
  - `DataSourceConfig` configures SQLite datasource.
  - `DatabaseInitializer` creates and seeds required tables on startup.

## 2) Operations

Implemented required SOAP operations:
- `GetMovies`
- `GetShowTimes(movieId)`
- `ReserveSeat(movieId, showTimeId, seatNumber, customerName)`
- `CancelReservation(reservationId)`
- `GetReservation(reservationId)`

All operations are exposed only via SOAP.

## 3) SOAP XML and WSDL

- Runtime WSDL: `http://localhost:8080/soap-api/ticket?wsdl`
- Static WSDL deliverable: `wsdl/ticket-service.wsdl`

Messages are exchanged in SOAP XML format (sample requests in `soap-tests/requests/`).

## 4) Error Handling (SOAP Fault)

Business and validation errors are returned as SOAP Fault through `TicketServiceException` and `FaultDetail`.

Sample error codes:
- `INVALID_ARGUMENT`
- `MOVIE_NOT_FOUND`
- `SHOWTIME_NOT_FOUND`
- `DUPLICATE_RESERVATION`
- `RESERVATION_NOT_FOUND`
- `ALREADY_CANCELLED`
- `DATABASE_ERROR`

## 5) Database

Database engine: SQLite

Required tables implemented:
- `movies(movie_id, title, genre, duration)`
- `show_times(showtime_id, movie_id, time)`
- `reservations(reservation_id, movie_id, showtime_id, seat, customer_name, status)`

Deliverables:
- SQL file: `database/schema.sql`
- SQLite file: `database/cinema.db`

## 6) SOAP Client

A Java SOAP client runner is included:
- `SoapClientRunner`

It demonstrates:
- `GetMovies`
- `GetShowTimes`
- `ReserveSeat`
- `GetReservation`
- `CancelReservation`
- duplicate reservation fault handling

Run with:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--runClient
```

## 7) Testing Summary

Manual SOAP tests can be executed in SoapUI/Postman using the sample XML requests.
Required tests are documented in:
- `soap-tests/TESTING.md`
