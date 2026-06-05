package org.soap.cinema.cinematicketsoap.service;

import jakarta.jws.WebService;
import org.soap.cinema.cinematicketsoap.exception.FaultDetail;
import org.soap.cinema.cinematicketsoap.exception.TicketServiceException;
import org.soap.cinema.cinematicketsoap.model.MovieListResponse;
import org.soap.cinema.cinematicketsoap.model.Reservation;
import org.soap.cinema.cinematicketsoap.model.ReservationResponse;
import org.soap.cinema.cinematicketsoap.model.ShowTimeListResponse;
import org.soap.cinema.cinematicketsoap.repository.RepositoryException;
import org.soap.cinema.cinematicketsoap.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@WebService(endpointInterface = "org.soap.cinema.cinematicketsoap.service.TicketService")
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public MovieListResponse getMovies() {
        try {
            return new MovieListResponse(ticketRepository.findMovies());
        } catch (RepositoryException exception) {
            throw new IllegalStateException("Failed to fetch movies.", exception);
        }
    }

    @Override
    public ShowTimeListResponse getShowTimes(String movieId) throws TicketServiceException {
        validateRequired(movieId, "movieId");
        return safeCall(() -> {
            if (!ticketRepository.existsMovieById(movieId)) {
                throw fault(
                        "MOVIE_NOT_FOUND",
                        "Movie with id '" + movieId + "' was not found."
                );
            }
            return new ShowTimeListResponse(ticketRepository.findShowTimesByMovieId(movieId));
        });
    }

    @Override
    public ReservationResponse reserveSeat(String movieId, String showTimeId, int seatNumber, String customerName)
            throws TicketServiceException {
        validateRequired(movieId, "movieId");
        validateRequired(showTimeId, "showTimeId");
        validateRequired(customerName, "customerName");
        if (seatNumber <= 0) {
            throw fault("INVALID_SEAT_NUMBER", "seatNumber must be greater than zero.");
        }

        return safeCall(() -> {
            if (!ticketRepository.existsMovieById(movieId)) {
                throw fault("MOVIE_NOT_FOUND", "Movie with id '" + movieId + "' was not found.");
            }
            if (!ticketRepository.existsShowTimeByIdAndMovieId(showTimeId, movieId)) {
                throw fault(
                        "SHOWTIME_NOT_FOUND",
                        "ShowTime with id '" + showTimeId + "' was not found for movie '" + movieId + "'."
                );
            }
            if (ticketRepository.existsSeatReservation(movieId, showTimeId, seatNumber)) {
                throw fault(
                        "DUPLICATE_RESERVATION",
                        "Seat " + seatNumber + " is already reserved for this show time."
                );
            }

            String reservationId = UUID.randomUUID().toString();
            Reservation reservation = new Reservation(
                    reservationId,
                    movieId,
                    showTimeId,
                    seatNumber,
                    customerName,
                    "CONFIRMED"
            );
            ticketRepository.insertReservation(reservation);
            return new ReservationResponse(reservationId, "CONFIRMED");
        });
    }

    @Override
    public ReservationResponse cancelReservation(String reservationId) throws TicketServiceException {
        validateRequired(reservationId, "reservationId");
        return safeCall(() -> {
            Reservation reservation = ticketRepository.findReservationById(reservationId)
                    .orElseThrow(() -> fault(
                            "RESERVATION_NOT_FOUND",
                            "Reservation with id '" + reservationId + "' was not found."
                    ));

            if ("CANCELLED".equalsIgnoreCase(reservation.getStatus())) {
                throw fault(
                        "ALREADY_CANCELLED",
                        "Reservation with id '" + reservationId + "' is already cancelled."
                );
            }

            Reservation updatedReservation = ticketRepository.updateReservationStatus(reservationId, "CANCELLED");
            return new ReservationResponse(updatedReservation.getReservationId(), updatedReservation.getStatus());
        });
    }

    @Override
    public Reservation getReservation(String reservationId) throws TicketServiceException {
        validateRequired(reservationId, "reservationId");
        return safeCall(() -> ticketRepository.findReservationById(reservationId)
                .orElseThrow(() -> fault(
                        "RESERVATION_NOT_FOUND",
                        "Reservation with id '" + reservationId + "' was not found."
                )));
    }

    private TicketServiceException fault(String code, String message) {
        return new TicketServiceException(message, new FaultDetail(code, message));
    }

    private void validateRequired(String value, String fieldName) throws TicketServiceException {
        if (value == null || value.isBlank()) {
            throw fault("INVALID_ARGUMENT", fieldName + " is required.");
        }
    }

    private <T> T safeCall(CheckedSupplier<T> supplier) throws TicketServiceException {
        try {
            return supplier.get();
        } catch (TicketServiceException exception) {
            throw exception;
        } catch (RepositoryException exception) {
            throw fault("DATABASE_ERROR", exception.getMessage());
        } catch (Exception exception) {
            throw new TicketServiceException(
                    "Unexpected service error",
                    new FaultDetail("INTERNAL_ERROR", exception.getMessage()),
                    exception
            );
        }
    }

    @FunctionalInterface
    private interface CheckedSupplier<T> {
        T get() throws Exception;
    }
}
