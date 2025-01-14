package com.PizzaDrone.ILPWebService.dataType;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class PizzaOrder {

    @JsonProperty("orderNo")
    private String orderNo;

    @JsonProperty("orderDate")
    private Date orderDate;

    @JsonProperty("priceTotalInPence")
    private double PriceTotal;

    @JsonProperty("pizzasInOrder")
    private PizzaDetails[] Pizzas;

    @JsonProperty("creditCardInformation")
    private CardInfo CreditCard;


    public String getOrderNo() {
        return orderNo;
    }

    public Date getOrderDate(){
        return orderDate;
    }

    public double getPriceTotalInPence(){
        return PriceTotal;
    }

    public PizzaDetails[] getPizzasInOrder(){
        return Pizzas;
    }

    public CardInfo getCreditCardInformation(){
        return CreditCard;
    }
}
