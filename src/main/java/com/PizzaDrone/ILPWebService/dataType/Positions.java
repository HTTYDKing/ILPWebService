package com.PizzaDrone.ILPWebService.dataType;

import com.fasterxml.jackson.annotation.JsonProperty;

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
        boolean inrange = !(this.longitude >= -180 && this.longitude <= 180) && (this.latitude >= -90 && this.latitude <= 90);

        return Double.isNaN(this.longitude) || Double.isNaN(this.latitude) || inrange;
    }

}
