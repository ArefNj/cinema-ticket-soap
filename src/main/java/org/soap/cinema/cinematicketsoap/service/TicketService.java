package org.soap.cinema.cinematicketsoap.service; // پکیج خودتان

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import org.soap.cinema.cinematicketsoap.model.Movie;
import org.soap.cinema.cinematicketsoap.model.MovieListResponse;
import org.soap.cinema.cinematicketsoap.model.ShowTimeListResponse;

import java.util.List;

@WebService
public interface TicketService {

    @WebMethod(operationName = "GetMovies")
    MovieListResponse getMovies();

    @WebMethod(operationName = "GetShowTimes")
    ShowTimeListResponse getShowTimes(@WebParam(name = "movieId") String movieId);

    // بقیه متدها را بعدا اینجا اضافه خواهید کرد
}
