package com.PizzaDrone.ILPWebService.dataType;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.*;

public class RegionRequest {

    @JsonProperty("position")
    private Point point;

    @JsonProperty("region")
    private RegionArea region;

    public Point getPoint() {
        return point;
    }

    public RegionArea getRegion() {
        return region;
    }
}
