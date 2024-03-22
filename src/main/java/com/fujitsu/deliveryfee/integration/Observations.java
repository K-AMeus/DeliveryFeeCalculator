package com.fujitsu.deliveryfee.integration;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "observations")
public class Observations {
    private List<StationData> stations;


    public Observations() {
    }


    @XmlElement(name = "station")
    public void setStations(List<StationData> stations) {
        this.stations = stations;
    }

    public List<StationData> getStations() {
        return stations;
    }
}
