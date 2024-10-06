package com.PizzaDrone.ILPWebService.dataType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LngLatAng {
    @JsonProperty("start")
    private Positions position;
    @JsonProperty("angle")
    private double angle;


    public Positions getPos() {
        return position;
    }
    public double angle() {
        return angle;
    }
}
