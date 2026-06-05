package org.soap.cinema.cinematicketsoap.exception;

import jakarta.xml.ws.WebFault;

@WebFault(name = "TicketServiceFault")
public class TicketServiceException extends Exception {

    private final FaultDetail faultInfo;

    public TicketServiceException(String message, FaultDetail faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public TicketServiceException(String message, FaultDetail faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    public FaultDetail getFaultInfo() {
        return faultInfo;
    }
}
