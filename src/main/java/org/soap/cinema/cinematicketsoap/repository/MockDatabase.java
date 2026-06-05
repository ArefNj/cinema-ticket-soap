package org.soap.cinema.cinematicketsoap.repository;

import org.soap.cinema.cinematicketsoap.model.Movie;
import java.util.ArrayList;
import java.util.List;

public class MovieDb {
    public static final List<Movie> movies = new ArrayList<>();

    // just runs once
    static {
        movies.add(new Movie("M101", "Interstellar", "Sci-Fi", 169));
        movies.add(new Movie("M102", "The Godfather", "Crime", 175));
        movies.add(new Movie("M103", "Inception", "Sci-Fi", 148));
    }
}
