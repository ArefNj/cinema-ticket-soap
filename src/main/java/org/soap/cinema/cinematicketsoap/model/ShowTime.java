package org.soap.cinema.cinematicketsoap.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShowTime")
@Entity
public class ShowTime {

    @Id
    @XmlElement(name = "showTimeId", required = true)
    private String showTimeId;
    @XmlElement(name = "movieId", required = true)
    private String movieId;
    @XmlElement(name = "time", required = true)
    private String time;

    public ShowTime() {
    }

    public ShowTime(String showTimeId, String movieId, String time) {
        this.showTimeId = showTimeId;
        this.movieId = movieId;
        this.time = time;
    }

    // --- getter & setter ---
    public String getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(String showTimeId) {
        this.showTimeId = showTimeId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
