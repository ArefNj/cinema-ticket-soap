package org.soap.cinema.cinematicketsoap.repository;

import org.soap.cinema.cinematicketsoap.model.Movie;
import org.soap.cinema.cinematicketsoap.model.ShowTime;

import java.util.ArrayList;
import java.util.List;

public class MockDatabase {
    public static final List<Movie> movies = new ArrayList<>();
    public static final List<ShowTime> showTimes = new ArrayList<>();
    // just runs once
    static {
        movies.add(new Movie("M101", "Interstellar", "Sci-Fi", 169));
        movies.add(new Movie("M102", "The Godfather", "Crime", 175));
        movies.add(new Movie("M103", "Inception", "Sci-Fi", 148));
        showTimes.add(new ShowTime("ST101", "M101", "2025-06-10 15:30"));
        showTimes.add(new ShowTime("ST102", "M102", "2025-06-11 15:30"));
        showTimes.add(new ShowTime("ST103", "M103", "2025-06-12 15:30"));
    }
}
