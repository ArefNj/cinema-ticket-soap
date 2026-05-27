package org.soap.cinema.cinematicketsoap.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://service.ticket.cinema.com/")
public interface TicketService {

    @WebMethod
    String bookTicket(@WebParam(name = "movieName") String movieName);
}
