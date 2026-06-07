package org.soap.cinema.cinematicketsoap.exception;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NoReservationFoundFault")
public class NoReservationFoundFault {
    @XmlElement(name = "message")
    private String message;

    public NoReservationFoundFault() {}
    public NoReservationFoundFault(String message) { this.message = message; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}