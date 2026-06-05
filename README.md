# Cinema Ticket SOAP

SOAP-based cinema ticket reservation system implemented with Java + Apache CXF + Spring Boot.

## Features
- `GetMovies`
- `GetShowTimes(movieId)`
- `ReserveSeat(movieId, showTimeId, seatNumber, customerName)`
- `CancelReservation(reservationId)`
- `GetReservation(reservationId)`
- SOAP Fault handling for invalid input, missing IDs, and duplicate reservation
- SQLite-backed persistence (movies, show_times, reservations)
- Built-in SOAP client runner for end-to-end invocation

## Project Structure
- Service interface: `src/main/java/org/soap/cinema/cinematicketsoap/service/TicketService.java`
- Service implementation: `src/main/java/org/soap/cinema/cinematicketsoap/service/TicketServiceImpl.java`
- Database repository: `src/main/java/org/soap/cinema/cinematicketsoap/repository/TicketRepository.java`
- SOAP server publish config: `src/main/java/org/soap/cinema/cinematicketsoap/config/CxfConfig.java`
- SQLite setup/seed: `src/main/java/org/soap/cinema/cinematicketsoap/config/DatabaseInitializer.java`
- SOAP client runner: `src/main/java/org/soap/cinema/cinematicketsoap/client/SoapClientRunner.java`

## Run Server
```bash
mvn spring-boot:run
```

SOAP endpoint:
- `http://localhost:8080/soap-api/ticket`

Generated WSDL (runtime):
- `http://localhost:8080/soap-api/ticket?wsdl`

## Run SOAP Client
Start server first, then run:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--runClient
```

## Database
- Runtime DB path is configured in `src/main/resources/application.yaml`:
  - `app.database.path: ./database/cinema.db`
- SQL schema + seed script is provided at:
  - `database/schema.sql`
- Prebuilt SQLite database file:
  - `database/cinema.db`

## SOAP Testing (SoapUI/Postman)
Sample SOAP XML requests are provided under:
- `soap-tests/requests/`

See complete testing instructions in:
- `soap-tests/TESTING.md`

## WSDL Deliverable
- Static project WSDL file: `wsdl/ticket-service.wsdl`
- Runtime-generated WSDL URL: `http://localhost:8080/soap-api/ticket?wsdl`

## Report
Project report is available at:
- `REPORT.md`
