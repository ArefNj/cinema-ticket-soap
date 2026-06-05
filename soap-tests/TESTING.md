# SOAP Testing Guide (SoapUI/Postman)

Endpoint:
- `http://localhost:8080/soap-api/ticket`

WSDL:
- `http://localhost:8080/soap-api/ticket?wsdl`

## Required Tests

1. **Send SOAP request**
   - Use any XML request from `soap-tests/requests/`.

2. **Receive SOAP response**
   - Ensure body contains corresponding response object for each operation.

3. **SOAP Fault test**
   - Run `reserve-seat-duplicate.xml` and verify SOAP fault with duplicate reservation error.

4. **Successful reservation test**
   - Run `reserve-seat-success.xml` and verify:
     - non-empty `reservationId`
     - `status = CONFIRMED`

5. **Duplicate reservation test**
   - Run `reserve-seat-duplicate.xml` and verify fault details:
     - `code = DUPLICATE_RESERVATION`

## Optional Additional Tests
- Invalid `movieId` for `GetShowTimes` => `MOVIE_NOT_FOUND`
- Invalid `reservationId` for `GetReservation` => `RESERVATION_NOT_FOUND`
- Cancel same reservation twice => second call returns `ALREADY_CANCELLED`
