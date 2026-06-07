package org.soap.cinema.cinematicketsoap.config;


import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.soap.cinema.cinematicketsoap.service.TicketServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CxfConfig {

    private final Bus bus;
    private final TicketServiceImpl ticketService;
    private final AuthenticationHandler authenticationHandler;

    public CxfConfig(Bus bus, TicketServiceImpl ticketService,
                     AuthenticationHandler authenticationHandler) {
        this.bus = bus;
        this.ticketService = ticketService;
        this.authenticationHandler = authenticationHandler;
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, ticketService);
        endpoint.setHandlers(List.of(authenticationHandler));
        endpoint.publish("/ticket");
        return endpoint;
    }
}