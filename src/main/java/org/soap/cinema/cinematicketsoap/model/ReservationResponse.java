package org.soap.cinema.cinematicketsoap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReservationResponse")
public class ReservationResponse {

    @XmlElement(name = "reservationId", required = true)
    private String reservationId;
    @XmlElement(name = "status", required = true)
    private String status;

    public ReservationResponse(String reservationId, String status) {
        this.reservationId = reservationId;
        this.status = status;
    }

    public ReservationResponse() {
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
