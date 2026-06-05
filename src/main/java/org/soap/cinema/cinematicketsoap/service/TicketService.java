package org.soap.cinema.cinematicketsoap.service; // پکیج خودتان

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.soap.cinema.cinematicketsoap.exception.DuplicateReservationException;
import org.soap.cinema.cinematicketsoap.model.MovieListResponse;
import org.soap.cinema.cinematicketsoap.model.Reservation;
import org.soap.cinema.cinematicketsoap.model.ReservationResponse;
import org.soap.cinema.cinematicketsoap.model.ShowTimeListResponse;

@WebService
public interface TicketService {

    @WebMethod(operationName = "GetMovies")
    MovieListResponse getMovies();

    @WebMethod(operationName = "GetShowTimes")
    ShowTimeListResponse getShowTimes(@WebParam(name = "movieId") String movieId);

    @WebMethod(operationName = "ReserveSeat")
    ReservationResponse reserveSeat(
            @WebParam(name = "movieId") String movieId,
            @WebParam(name = "showTimeId") String showTimeId,
            @WebParam(name = "seatNumber") int seatNumber,
            @WebParam(name = "customerName") String customerName
    ) throws DuplicateReservationException;

//    @WebMethod
//    boolean cancelReservation(@WebParam(name = "reservationId") int reservationId) throws Exception;
//
//    @WebMethod
//    Reservation getReservation(@WebParam(name = "reservationId") int reservationId) throws Exception;
}
