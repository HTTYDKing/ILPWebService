package com.PizzaDrone.ILPWebService.dataType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Resturant {
    @JsonProperty("name")
    private String resturantName;
    @JsonProperty("location")
    private Positions location;
    @JsonProperty("openingDays")
    private String[] openingDates;
    @JsonProperty("menu")
    private PizzaDetails[] menu;

    public PizzaDetails[] getmenu() {
        return menu;
    }
    public String[] openingDates() {
        return openingDates;
    }
    public Positions getLocation() {
        return location;
    }
}
