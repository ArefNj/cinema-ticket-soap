package org.soap.cinema.cinematicketsoap.repository;
import org.soap.cinema.cinematicketsoap.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String> {
}
