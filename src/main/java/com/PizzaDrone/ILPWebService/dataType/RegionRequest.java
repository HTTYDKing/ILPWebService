package com.PizzaDrone.ILPWebService.dataType;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.*;

public class RegionRequest {

    @JsonProperty("position")
    private Positions point;

    @JsonProperty("region")
    private RegionArea region;

    public Positions getPoint() {
        return point;
    }

    public RegionArea getRegion() {
        return region;
    }
}
