package org.soap.cinema.cinematicketsoap.repository;
import org.soap.cinema.cinematicketsoap.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    boolean existsByMovieIdAndShowTimeIdAndSeatNumber(String movieId, String showTimeId, int seatNumber);
}
