package org.soap.cinema.cinematicketsoap.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import org.soap.cinema.cinematicketsoap.exception.TicketServiceException;
import org.soap.cinema.cinematicketsoap.model.MovieListResponse;
import org.soap.cinema.cinematicketsoap.model.Reservation;
import org.soap.cinema.cinematicketsoap.model.ReservationResponse;
import org.soap.cinema.cinematicketsoap.model.ShowTimeListResponse;

@WebService(
        name = "TicketService",
        serviceName = "TicketService",
        targetNamespace = "http://service.cinematicketsoap.cinema.soap.org/"
)
public interface TicketService {

    @WebMethod(operationName = "GetMovies")
    @WebResult(name = "movies")
    MovieListResponse getMovies();

    @WebMethod(operationName = "GetShowTimes")
    @WebResult(name = "showTimes")
    ShowTimeListResponse getShowTimes(@WebParam(name = "movieId") String movieId) throws TicketServiceException;

    @WebMethod(operationName = "ReserveSeat")
    @WebResult(name = "reservation")
    ReservationResponse reserveSeat(
            @WebParam(name = "movieId") String movieId,
            @WebParam(name = "showTimeId") String showTimeId,
            @WebParam(name = "seatNumber") int seatNumber,
            @WebParam(name = "customerName") String customerName
    ) throws TicketServiceException;

    @WebMethod(operationName = "CancelReservation")
    @WebResult(name = "cancellation")
    ReservationResponse cancelReservation(
            @WebParam(name = "reservationId") String reservationId
    ) throws TicketServiceException;

    @WebMethod(operationName = "GetReservation")
    @WebResult(name = "reservation")
    Reservation getReservation(
            @WebParam(name = "reservationId") String reservationId
    ) throws TicketServiceException;
}
