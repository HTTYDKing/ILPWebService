package com.PizzaDrone.ILPWebService.dataType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PizzaDetails {
    @JsonProperty("name")
    private String pizzaName;

    @JsonProperty("priceInPence")
    private double pizzaPrice;

    public String getPizzaName() {
        return pizzaName;
    }
    public double getPizzaPrice() {
        return pizzaPrice;
    }
}
