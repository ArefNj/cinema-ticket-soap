package org.soap.cinema.cinematicketsoap.config;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DatabaseInitializer {

    private final DataSource dataSource;

    public DatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @jakarta.annotation.PostConstruct
    public void initialize() {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            createTables(connection);
            seedMovies(connection);
            seedShowTimes(connection);
            seedReservations(connection);
            connection.commit();
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to initialize SQLite database", exception);
        }
    }

    private void createTables(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS movies (
                        movie_id TEXT PRIMARY KEY,
                        title TEXT NOT NULL,
                        genre TEXT NOT NULL,
                        duration INTEGER NOT NULL
                    )
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS show_times (
                        showtime_id TEXT PRIMARY KEY,
                        movie_id TEXT NOT NULL,
                        time TEXT NOT NULL,
                        FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
                    )
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS reservations (
                        reservation_id TEXT PRIMARY KEY,
                        movie_id TEXT NOT NULL,
                        showtime_id TEXT NOT NULL,
                        seat INTEGER NOT NULL,
                        customer_name TEXT NOT NULL,
                        status TEXT NOT NULL,
                        FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
                        FOREIGN KEY (showtime_id) REFERENCES show_times(showtime_id)
                    )
                    """);
        }
    }

    private void seedMovies(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                INSERT OR IGNORE INTO movies(movie_id, title, genre, duration) VALUES (?, ?, ?, ?)
                """)) {
            addMovie(statement, "M101", "Interstellar", "Sci-Fi", 169);
            addMovie(statement, "M102", "The Godfather", "Crime", 175);
            addMovie(statement, "M103", "Inception", "Sci-Fi", 148);
        }
    }

    private void addMovie(PreparedStatement statement, String movieId, String title, String genre, int duration)
            throws SQLException {
        statement.setString(1, movieId);
        statement.setString(2, title);
        statement.setString(3, genre);
        statement.setInt(4, duration);
        statement.executeUpdate();
    }

    private void seedShowTimes(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                INSERT OR IGNORE INTO show_times(showtime_id, movie_id, time) VALUES (?, ?, ?)
                """)) {
            addShowTime(statement, "ST101", "M101", "2026-06-10 15:30");
            addShowTime(statement, "ST102", "M102", "2026-06-11 15:30");
            addShowTime(statement, "ST103", "M103", "2026-06-12 15:30");
            addShowTime(statement, "ST104", "M101", "2026-06-13 20:00");
        }
    }

    private void addShowTime(PreparedStatement statement, String showTimeId, String movieId, String time)
            throws SQLException {
        statement.setString(1, showTimeId);
        statement.setString(2, movieId);
        statement.setString(3, time);
        statement.executeUpdate();
    }

    private void seedReservations(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                INSERT OR IGNORE INTO reservations(reservation_id, movie_id, showtime_id, seat, customer_name, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """)) {
            addReservation(statement, "R101", "M101", "ST101", 1, "Bob", "CONFIRMED");
            addReservation(statement, "R102", "M101", "ST101", 2, "Alice", "CONFIRMED");
            addReservation(statement, "R103", "M102", "ST102", 3, "Michel", "CONFIRMED");
        }
    }

    private void addReservation(
            PreparedStatement statement,
            String reservationId,
            String movieId,
            String showTimeId,
            int seat,
            String customerName,
            String status
    ) throws SQLException {
        statement.setString(1, reservationId);
        statement.setString(2, movieId);
        statement.setString(3, showTimeId);
        statement.setInt(4, seat);
        statement.setString(5, customerName);
        statement.setString(6, status);
        statement.executeUpdate();
    }
}
