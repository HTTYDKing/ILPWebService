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

    public boolean NotValid(){
        boolean notValidverticies = false;
        boolean notenclosed = false;

        for (Positions value : this.vertices) {
            if (value.NotValid()) {
                notValidverticies = true;
            }
        }

        if ((this.vertices[0].getLat() != this.vertices[this.vertices.length - 1].getLat())||
                (this.vertices[0].getLng() != this.vertices[this.vertices.length - 1].getLng())) {
            notenclosed = true;
        }

        return name == null || notValidverticies || notenclosed;
    }


}
