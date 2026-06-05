package org.soap.cinema.cinematicketsoap.repository;

import org.soap.cinema.cinematicketsoap.model.Movie;
import org.soap.cinema.cinematicketsoap.model.Reservation;
import org.soap.cinema.cinematicketsoap.model.ShowTime;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepository {

    private final DataSource dataSource;

    public TicketRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Movie> findMovies() {
        String sql = "SELECT movie_id, title, genre, duration FROM movies ORDER BY movie_id";
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                movies.add(
                        new Movie(
                                resultSet.getString("movie_id"),
                                resultSet.getString("title"),
                                resultSet.getString("genre"),
                                resultSet.getInt("duration")
                        )
                );
            }
            return movies;
        } catch (SQLException exception) {
            throw new RepositoryException("Failed to fetch movies", exception);
        }
    }

    public boolean existsMovieById(String movieId) {
        String sql = "SELECT 1 FROM movies WHERE movie_id = ?";
        return existsBySql(sql, movieId);
    }

    public List<ShowTime> findShowTimesByMovieId(String movieId) {
        String sql = "SELECT showtime_id, movie_id, time FROM show_times WHERE movie_id = ? ORDER BY showtime_id";
        List<ShowTime> showTimes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, movieId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    showTimes.add(
                            new ShowTime(
                                    resultSet.getString("showtime_id"),
                                    resultSet.getString("movie_id"),
                                    resultSet.getString("time")
                            )
                    );
                }
            }
            return showTimes;
        } catch (SQLException exception) {
            throw new RepositoryException("Failed to fetch show times", exception);
        }
    }

    public boolean existsShowTimeByIdAndMovieId(String showTimeId, String movieId) {
        String sql = "SELECT 1 FROM show_times WHERE showtime_id = ? AND movie_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, showTimeId);
            statement.setString(2, movieId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            throw new RepositoryException("Failed to check show time", exception);
        }
    }

    public boolean existsSeatReservation(String movieId, String showTimeId, int seatNumber) {
        String sql = """
                SELECT 1
                FROM reservations
                WHERE movie_id = ? AND showtime_id = ? AND seat = ? AND status = 'CONFIRMED'
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, movieId);
            statement.setString(2, showTimeId);
            statement.setInt(3, seatNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            throw new RepositoryException("Failed to check seat reservation", exception);
        }
    }

    public Reservation insertReservation(Reservation reservation) {
        String sql = """
                INSERT INTO reservations(reservation_id, movie_id, showtime_id, seat, customer_name, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, reservation.getReservationId());
            statement.setString(2, reservation.getMovieId());
            statement.setString(3, reservation.getShowTimeId());
            statement.setInt(4, reservation.getSeatNumber());
            statement.setString(5, reservation.getCustomerName());
            statement.setString(6, reservation.getStatus());
            statement.executeUpdate();
            return reservation;
        } catch (SQLException exception) {
            throw new RepositoryException("Failed to insert reservation", exception);
        }
    }

    public Optional<Reservation> findReservationById(String reservationId) {
        String sql = """
                SELECT reservation_id, movie_id, showtime_id, seat, customer_name, status
                FROM reservations
                WHERE reservation_id = ?
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, reservationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                Reservation reservation = new Reservation(
                        resultSet.getString("reservation_id"),
                        resultSet.getString("movie_id"),
                        resultSet.getString("showtime_id"),
                        resultSet.getInt("seat"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("status")
                );
                return Optional.of(reservation);
            }
        } catch (SQLException exception) {
            throw new RepositoryException("Failed to fetch reservation", exception);
        }
    }

    public Reservation updateReservationStatus(String reservationId, String status) {
        String sql = "UPDATE reservations SET status = ? WHERE reservation_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setString(2, reservationId);
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) {
                throw new RepositoryException("No reservation updated for id: " + reservationId);
            }
            return findReservationById(reservationId)
                    .orElseThrow(() -> new RepositoryException("Reservation disappeared after update: " + reservationId));
        } catch (SQLException exception) {
            throw new RepositoryException("Failed to update reservation status", exception);
        }
    }

    private boolean existsBySql(String sql, String value) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            throw new RepositoryException("Failed existence check", exception);
        }
    }
}
