package com.PizzaDrone.ILPWebService.dataType;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardInfo {
    @JsonProperty("creditCardNumber")
    private String CardNumber;
    @JsonProperty("creditCardExpiry")
    private String ExpireDate;
    @JsonProperty("cvv")
    private String cvv;

    public String getCardNumber() {
        return CardNumber;
    }
    public Date getExpireDate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");

        return formatter.parse(ExpireDate);
    }

    public String getCvv() {
        return cvv;
    }

}
