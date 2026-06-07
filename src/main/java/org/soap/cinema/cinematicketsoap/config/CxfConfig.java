package org.soap.cinema.cinematicketsoap.config;


import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.handler.Handler;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.soap.cinema.cinematicketsoap.service.TicketServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CxfConfig {

    private final Bus bus;
    private final TicketServiceImpl ticketService;
    private final AuthenticationHandler authenticationHandler; // اضافه کن

    public CxfConfig(Bus bus, TicketServiceImpl ticketService,
                     AuthenticationHandler authenticationHandler) { // اضافه کن
        this.bus = bus;
        this.ticketService = ticketService;
        this.authenticationHandler = authenticationHandler; // اضافه کن
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, ticketService);

        // قبل از publish
        List<Handler> handlers = new ArrayList<>();
        handlers.add(authenticationHandler);
        endpoint.setHandlers(handlers);

        endpoint.publish("/ticket");
        return endpoint;
    }
}