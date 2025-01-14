package com.PizzaDrone.ILPWebService;

import com.PizzaDrone.ILPWebService.dataType.PizzaOrder;
import com.PizzaDrone.ILPWebService.dataType.Positions;
import com.PizzaDrone.ILPWebService.dataType.RegionArea;
import com.PizzaDrone.ILPWebService.dataType.RegionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class CalDeliveryPathTest {
    String Json = "{\"orderNo\": \"49F980A3\",\"orderDate\": \"2025-01-12\"," +
            "\"priceTotalInPence\": 2500,\"pizzasInOrder\": [{\"name\": \"R1: Margarita\",\"priceInPence\": 1000},{\"name\": \"R1: Calzone\"," +
            "\"priceInPence\": 1400} ],\"creditCardInformation\": {\"creditCardNumber\": \"4984741065500978\"," +
            "\"creditCardExpiry\": \"10/25\",\"cvv\": \"045\"}}";
    @Test
    void TestPath() {

        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzaOrder regionRequest = mapper.readValue(Json, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(regionRequest);
            CalDeliveryPath DronePathtest = new CalDeliveryPath(validateOrder.getOrder(), validateOrder.getResturantorder());
            Positions[] path = DronePathtest.getFlightPath();
            System.out.println(Arrays.toString(path));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }


}
