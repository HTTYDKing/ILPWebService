package com.PizzaDrone.ILPWebService.dataType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Positions {
    @JsonProperty("lat")
    private double latitude;
    @JsonProperty("lng")
    private double longitude;

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

}
