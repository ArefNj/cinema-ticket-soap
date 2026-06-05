package org.soap.cinema.cinematicketsoap.model;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShowTimeList")
public class ShowTimeListResponse {
    @XmlElement(name = "ShowTimes")
    private List<ShowTime> showTimes;

    public ShowTimeListResponse() {
    }

    public ShowTimeListResponse(List<ShowTime> showTimes) {
        this.showTimes = showTimes;
    }

    public List<ShowTime> getShowTimes() {
        return showTimes;
    }
    public void setShowTimes(List<ShowTime> showTimes) {
        this.showTimes = showTimes;
    }
}
