package org.soap.cinema.cinematicketsoap.exception;

import jakarta.xml.ws.WebFault;

@WebFault(name = "DuplicateReservationFault")
public class DuplicateReservationException extends Exception {
    public DuplicateReservationException(String message) {
        super(message);
    }
}
