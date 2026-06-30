package org.soap.cinema.cinematicketsoap.service;

import jakarta.jws.WebService;
import org.soap.cinema.cinematicketsoap.exception.DuplicateReservationException;
import org.soap.cinema.cinematicketsoap.exception.NoReservationFound;
import org.soap.cinema.cinematicketsoap.exception.NoReservationFoundFault;
import org.soap.cinema.cinematicketsoap.model.MovieListResponse;
import org.soap.cinema.cinematicketsoap.model.Reservation;
import org.soap.cinema.cinematicketsoap.model.ReservationResponse;
import org.soap.cinema.cinematicketsoap.model.ShowTimeListResponse;
import org.soap.cinema.cinematicketsoap.repository.MovieRepository;
import org.soap.cinema.cinematicketsoap.repository.ReservationRepository;
import org.soap.cinema.cinematicketsoap.repository.ShowTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@WebService(endpointInterface = "org.soap.cinema.cinematicketsoap.service.TicketService")
public class TicketServiceImpl implements TicketService {

    private final MovieRepository movieRepository;
    private final ShowTimeRepository showTimeRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public TicketServiceImpl(MovieRepository movieRepository,
                             ShowTimeRepository showTimeRepository, ReservationRepository reservationRepository) {
        this.movieRepository = movieRepository;
        this.showTimeRepository = showTimeRepository;
        this.reservationRepository = reservationRepository;
    }


    @Override
    public MovieListResponse getMovies() {
        return new MovieListResponse(movieRepository.findAll());
    }

    @Override
    public ShowTimeListResponse getShowTimes(String movieId) {
        return new ShowTimeListResponse(showTimeRepository.findByMovieId(movieId));
    }

    @Override
    public ReservationResponse reserveSeat(String movieId, String showTimeId,
                                           int seatNumber, String customerName) throws DuplicateReservationException {
        // IS DUPLICATE ?
        if (reservationRepository.existsByMovieIdAndShowTimeIdAndSeatNumber(movieId, showTimeId, seatNumber)) {
            throw new DuplicateReservationException("Seat " + seatNumber + " is already reserved for this showtime.");
        }

        String reservationId = UUID.randomUUID().toString();
        Reservation reservation =
                new Reservation(reservationId, movieId, showTimeId, seatNumber, customerName, "CONFIRMED");
        reservationRepository.save(reservation);
        return new ReservationResponse(reservationId, "CONFIRMED");
    }

    @Override
    public ReservationResponse cancelReservation(String reservationId) throws NoReservationFound {
        Reservation canceledReservation = getReservation(reservationId);
        reservationRepository.delete(canceledReservation);

        return new ReservationResponse(reservationId, "CANCELLED");

    }

    @Override
    public Reservation getReservation(String reservationId) throws NoReservationFound {
        var temp = reservationRepository.findById(reservationId);
        if (temp.isEmpty()) {
            throw new NoReservationFound(
                    "No reservation found by this id: " + reservationId,
                    new NoReservationFoundFault("No reservation found by this id: " + reservationId)
            );        }
        return temp.get();
    }

}
