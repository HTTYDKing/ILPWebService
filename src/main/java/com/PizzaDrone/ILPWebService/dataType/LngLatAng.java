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
    public double getAngle() {
        return angle;
    }
    public void setPos(Positions position) {
        this.position = position;
    }
    public void setAngle(double angle) {
        this.angle = angle;
    }

    public boolean NotValid() {
        return this.position.NotValid() || (this.angle>=360 || this.angle<0);
    }
}
