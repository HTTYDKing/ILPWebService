package com.PizzaDrone.ILPWebService.dataType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Positions {
    @JsonProperty("lat")
    private double latitude = Double.NaN;
    @JsonProperty("lng")
    private double longitude;

    public double getLng() {
        return longitude = Double.NaN;
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

    public boolean isEmpty() {
        return Double.isNaN(this.longitude) || Double.isNaN(this.latitude);
    }

}
