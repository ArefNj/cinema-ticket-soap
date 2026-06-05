package org.soap.cinema.cinematicketsoap.service; // پکیج خودتان

import jakarta.jws.WebService;
import org.soap.cinema.cinematicketsoap.model.MovieListResponse;
import org.soap.cinema.cinematicketsoap.model.Reservation;
import org.soap.cinema.cinematicketsoap.model.ReservationResponse;
import org.soap.cinema.cinematicketsoap.model.ShowTimeListResponse;
import org.soap.cinema.cinematicketsoap.repository.MockDatabase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@WebService(endpointInterface = "org.soap.cinema.cinematicketsoap.service.TicketService")
// آدرس پکیج دقیق اینترفیس خودتان
public class TicketServiceImpl implements TicketService {

    @Override
    public MovieListResponse getMovies() {

        return new MovieListResponse(MockDatabase.movies);

    }

    @Override
    public ShowTimeListResponse getShowTimes(String movieId) {
        return new ShowTimeListResponse(MockDatabase.showTimes.stream()
                .filter(showTime -> movieId.equals(showTime.getMovieId()))
                .toList());
    }

    @Override
    public ReservationResponse reserveSeat(String movieId, String showTimeId, int seatNumber, String customerName) {
        if(MockDatabase.reservations.stream()
                .anyMatch(reservation ->
                                reservation.getMovieId().equals(movieId) &&
                                reservation.getShowTimeId().equals(showTimeId) &&
                                reservation.getSeatNumber() == seatNumber
        )){
            return new ReservationResponse(UUID.randomUUID().toString(),"DUPLICATE");
        }

        String reservationId = UUID.randomUUID().toString();
        Reservation reservation = new Reservation(reservationId,movieId,showTimeId,seatNumber,customerName,"CONFIRMED");
        MockDatabase.reservations.add(reservation);

        return new ReservationResponse(reservationId,"CONFIRMED");

    }
}
