package org.soap.cinema.cinematicketsoap.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Movie")
@Entity
public class Movie {
    @Id
    @XmlElement(name = "movieId", required = true)
    private String movieId;

    @XmlElement(name = "title", required = true)
    private String title;

    @XmlElement(name = "genre")
    private String genre;

    @XmlElement(name = "duration")
    private int duration;

    // default for JAXB
    public Movie() {
    }

    public Movie(String movieId, String title, String genre, int duration) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
    }

    // --- getter & setter ---
    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
