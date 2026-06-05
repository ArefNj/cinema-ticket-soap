package org.soap.cinema.cinematicketsoap.client;

import org.soap.cinema.cinematicketsoap.exception.TicketServiceException;
import org.soap.cinema.cinematicketsoap.model.Reservation;
import org.soap.cinema.cinematicketsoap.model.ReservationResponse;
import org.soap.cinema.cinematicketsoap.service.TicketService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import java.net.URL;

@Component
public class SoapClientRunner implements CommandLineRunner {

    private static final String CLIENT_FLAG = "--runClient";
    private static final String WSDL_LOCATION = "http://localhost:8080/soap-api/ticket?wsdl";
    private static final QName SERVICE_NAME = new QName(
            "http://service.cinematicketsoap.cinema.soap.org/",
            "TicketService"
    );

    @Override
    public void run(String... args) throws Exception {
        if (!containsClientFlag(args)) {
            return;
        }

        jakarta.xml.ws.Service service = jakarta.xml.ws.Service.create(
                new URL(WSDL_LOCATION),
                SERVICE_NAME
        );
        TicketService ticketService = service.getPort(TicketService.class);

        System.out.println("SOAP CLIENT -> GetMovies");
        System.out.println(ticketService.getMovies().getMovies().size() + " movies loaded.");

        System.out.println("SOAP CLIENT -> GetShowTimes(M101)");
        System.out.println(ticketService.getShowTimes("M101").getShowTimes().size() + " show times found.");

        System.out.println("SOAP CLIENT -> ReserveSeat");
        ReservationResponse reservation = ticketService.reserveSeat("M101", "ST101", 9, "ClientRunner");
        System.out.println("Reservation id: " + reservation.getReservationId() + ", status: " + reservation.getStatus());

        System.out.println("SOAP CLIENT -> GetReservation");
        Reservation reservationInfo = ticketService.getReservation(reservation.getReservationId());
        System.out.println("Reservation seat: " + reservationInfo.getSeatNumber());

        System.out.println("SOAP CLIENT -> CancelReservation");
        ReservationResponse cancelled = ticketService.cancelReservation(reservation.getReservationId());
        System.out.println("Cancel status: " + cancelled.getStatus());

        try {
            ticketService.reserveSeat("M101", "ST101", 1, "Duplicate-Test");
        } catch (TicketServiceException exception) {
            String code = exception.getFaultInfo() != null ? exception.getFaultInfo().getCode() : "UNKNOWN";
            System.out.println("Expected SOAP Fault: " + code + " -> " + exception.getMessage());
        }
    }

    private boolean containsClientFlag(String[] args) {
        for (String arg : args) {
            if (CLIENT_FLAG.equalsIgnoreCase(arg)) {
                return true;
            }
        }
        return false;
    }
}
