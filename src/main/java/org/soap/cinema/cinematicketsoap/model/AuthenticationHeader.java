package org.soap.cinema.cinematicketsoap.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthHeader")
@XmlRootElement(name = "AuthHeader", namespace = "http://auth.cinema.org/")
public class AuthenticationHeader {

    @XmlElement(name = "username")
    private String username;

    @XmlElement(name = "password")
    private String password;



    // default constructor + getter & setter

    public AuthenticationHeader() {
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
