package org.soap.cinema.cinematicketsoap.service; // پکیج خودتان

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.soap.cinema.cinematicketsoap.model.MovieListResponse;
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
    );

    // بقیه متدها را بعدا اینجا اضافه خواهید کرد
}
