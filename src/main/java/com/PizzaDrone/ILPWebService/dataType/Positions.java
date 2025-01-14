package com.PizzaDrone.ILPWebService.dataType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonPropertyOrder({ "lng", "lat" })
public class Positions {
    @JsonProperty("lng")
    private double longitude = Double.NaN;
    @JsonProperty("lat")
    private double latitude = Double.NaN;


    public double getLng() {
        return longitude;
    }

    public double getLat() {
        return latitude;
    }

    public void setLng(double lng) {
        this.longitude = lng;
    }

    public void setLat(double lat) {
        this.latitude = lat;
    }

    public boolean NotValid() {
        boolean notinrange = !((this.longitude >= -180 && this.longitude <= 180) && (this.latitude >= -90 && this.latitude <= 90));

        return Double.isNaN(this.longitude) || Double.isNaN(this.latitude) || notinrange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Positions positions = (Positions) o;
        return Double.compare(positions.latitude,latitude) == 0 && Double.compare(positions.longitude,longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return "Position{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
