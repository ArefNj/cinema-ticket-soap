package org.soap.cinema.cinematicketsoap.exception;

import jakarta.xml.ws.WebFault;

@WebFault(name = "NoReservationFoundFault")
public class NoReservationFound extends Exception {
    private final NoReservationFoundFault faultInfo;

    public NoReservationFound(String message, NoReservationFoundFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public NoReservationFoundFault getFaultInfo() { return faultInfo; }
}
