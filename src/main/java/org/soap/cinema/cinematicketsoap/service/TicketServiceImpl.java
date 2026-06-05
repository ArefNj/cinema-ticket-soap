package org.soap.cinema.cinematicketsoap.service; // پکیج خودتان

import jakarta.jws.WebService;
import org.soap.cinema.cinematicketsoap.model.MovieListResponse;
import org.soap.cinema.cinematicketsoap.model.ShowTimeListResponse;
import org.soap.cinema.cinematicketsoap.repository.MockDatabase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
}
