package org.soap.cinema.cinematicketsoap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MovieList")
public class MovieListResponse {

    @XmlElement(name = "Movie")
    private List<Movie> movies;

    public MovieListResponse() {}

    public MovieListResponse(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() { return movies; }
    public void setMovies(List<Movie> movies) { this.movies = movies; }
}
