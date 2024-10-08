package com.PizzaDrone.ILPWebService.dataType;

import com.fasterxml.jackson.annotation.JsonProperty;


public class RegionArea {

    @JsonProperty("name")
    private String name;

    @JsonProperty("vertices")
    private Positions[] vertices;


    public Positions[] getVertices() {
        return vertices;
    }


}
