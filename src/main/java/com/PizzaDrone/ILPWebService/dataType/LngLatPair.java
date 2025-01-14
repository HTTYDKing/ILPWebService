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
    public void setPos1(Positions position1) {
        this.position1 = position1;
    }
    public void setPos2(Positions position2) {
        this.position2 = position2;
    }

    public boolean NotValid() {
        return this.position1.NotValid() || this.position2.NotValid();
    }
}
