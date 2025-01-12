package com.PizzaDrone.ILPWebService.dataType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "orderStatus", "orderValidationCode" })
public class OrderValidation {
    @JsonProperty("orderStatus")
    private OrderStatus orderStatus;
    @JsonProperty("orderValidationCode")
    private OrderValidationCode orderValidationCode;

    public OrderValidation(OrderStatus orderStatus, OrderValidationCode orderValidationCode) {
        this.orderStatus = orderStatus;
        this.orderValidationCode = orderValidationCode;
    }
}
