package org.soap.cinema.cinematicketsoap.repository;
import org.soap.cinema.cinematicketsoap.model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShowTimeRepository extends JpaRepository<ShowTime, String> {
    List<ShowTime> findByMovieId(String movieId);
}
