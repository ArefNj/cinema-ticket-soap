package org.soap.cinema.cinematicketsoap.service;

import jakarta.jws.WebService;
import org.springframework.stereotype.Service;

@Service
@WebService(
        serviceName = "TicketService",
        portName = "TicketServicePort",
        targetNamespace = "http://service.ticket.cinema.com/",
        endpointInterface = "org.soap.cinema.cinematicketsoap.service.TicketService"
)
public class TicketServiceImpl implements TicketService {

    @Override
    public String bookTicket(String movieName) {
        return "Ticket successfully reserved for: " + movieName;
    }
}
