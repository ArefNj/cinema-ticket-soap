package org.soap.cinema.cinematicketsoap.config;


import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.soap.cinema.cinematicketsoap.service.TicketServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CxfConfig {

    private final Bus bus;
    private final TicketServiceImpl ticketService;

    // تزریق وابستگی‌ها از طریق سازنده (Constructor Injection)
    public CxfConfig(Bus bus, TicketServiceImpl ticketService) {
        this.bus = bus;
        this.ticketService = ticketService;
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, ticketService);
        // مسیری که سرویس روی آن منتشر می‌شود
        endpoint.publish("/ticket");
        return endpoint;
    }
}
