package org.soap.cinema.cinematicketsoap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Reservation")
public class Reservation {
    @XmlElement(name = "reservationId", required = true)
    private String reservationId;
    @XmlElement(name = "movieId", required = true)
    private String movieId;
    @XmlElement(name = "showTimeId", required = true)
    private String showTimeId;
    @XmlElement(name = "seatNumber", required = true)
    private int seatNumber;
    @XmlElement(name = "customerName", required = true)
    private String customerName;
    @XmlElement(name = "status", required = true)
    private String status;

    public Reservation(String reservationId, String movieId, String showTimeId,
                       int seatNumber, String customerName, String status) {
        this.reservationId = reservationId;
        this.movieId = movieId;
        this.showTimeId = showTimeId;
        this.seatNumber = seatNumber;
        this.customerName = customerName;
        this.status = status;
    }


    public Reservation() {
    }


    public String getReservationId() {
        return reservationId;
    }
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }
    public String getMovieId() {
        return movieId;
    }
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
    public String getShowTimeId() {
        return showTimeId;
    }
    public void setShowTimeId(String showTimeId) {
        this.showTimeId = showTimeId;
    }
    public int getSeatNumber() {
        return seatNumber;
    }
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
