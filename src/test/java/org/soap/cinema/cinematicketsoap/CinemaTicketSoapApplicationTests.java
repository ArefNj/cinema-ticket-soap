package org.soap.cinema.cinematicketsoap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.soap.cinema.cinematicketsoap.exception.TicketServiceException;
import org.soap.cinema.cinematicketsoap.model.Reservation;
import org.soap.cinema.cinematicketsoap.model.ReservationResponse;
import org.soap.cinema.cinematicketsoap.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CinemaTicketSoapApplicationTests {

    @Autowired
    private TicketService ticketService;

    @Test
    void contextLoads() {
    }

    @Test
    void getMoviesShouldReturnSeedData() {
        Assertions.assertTrue(ticketService.getMovies().getMovies().size() >= 3);
    }

    @Test
    void reserveGetAndCancelShouldWork() throws TicketServiceException {
        int seatNumber = (int) ((System.nanoTime() % 100000) + 100);
        ReservationResponse reserveResponse = ticketService.reserveSeat("M101", "ST101", seatNumber, "JUnitUser");
        Assertions.assertNotNull(reserveResponse.getReservationId());
        Assertions.assertEquals("CONFIRMED", reserveResponse.getStatus());

        Reservation reservation = ticketService.getReservation(reserveResponse.getReservationId());
        Assertions.assertEquals(seatNumber, reservation.getSeatNumber());
        Assertions.assertEquals("CONFIRMED", reservation.getStatus());

        ReservationResponse cancelResponse = ticketService.cancelReservation(reserveResponse.getReservationId());
        Assertions.assertEquals("CANCELLED", cancelResponse.getStatus());
    }

    @Test
    void duplicateReservationShouldThrowSoapFault() {
        TicketServiceException exception = Assertions.assertThrows(
                TicketServiceException.class,
                () -> ticketService.reserveSeat("M101", "ST101", 1, "JUnitDuplicate")
        );
        Assertions.assertNotNull(exception.getFaultInfo());
        Assertions.assertEquals("DUPLICATE_RESERVATION", exception.getFaultInfo().getCode());
    }

}
