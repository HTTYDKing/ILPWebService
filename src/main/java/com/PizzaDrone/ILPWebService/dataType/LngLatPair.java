package com.PizzaDrone.ILPWebService.dataType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LngLatPair {
    @JsonProperty("position1")
    private Positions position1;
    @JsonProperty("position2")
    private Positions position2;


    public Positions getPos1() {
        return position1;
    }
    public Positions getPos2() {
        return position2;
    }
}
