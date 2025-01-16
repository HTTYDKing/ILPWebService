package com.PizzaDrone.ILPWebService;

import com.PizzaDrone.ILPWebService.dataType.LngLatPair;
import com.PizzaDrone.ILPWebService.dataType.PizzaOrder;
import com.PizzaDrone.ILPWebService.dataType.Positions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CalDeliveryPathTest {
    String Json = "{\n" +
            "    \"orderNo\": \"681FBE91\",\n" +
            "    \"orderDate\": \"2025-01-15\",\n" +
            "    \"priceTotalInPence\": 2600,\n" +
            "    \"pizzasInOrder\": [\n" +
            "      {\n" +
            "        \"name\": \"R2: Meat Lover\",\n" +
            "        \"priceInPence\": 1400\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"R2: Vegan Delight\",\n" +
            "        \"priceInPence\": 1100\n" +
            "      }\n" +
            "    ],\n" +
            "    \"creditCardInformation\": {\n" +
            "      \"creditCardNumber\": \"5130684162297637\",\n" +
            "      \"creditCardExpiry\": \"05/25\",\n" +
            "      \"cvv\": \"016\"\n" +
            "    }\n" +
            "  }";
    @Test
    void TestPath() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzaOrder regionRequest = mapper.readValue(Json, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(regionRequest);
            CalDeliveryPath DronePathtest = new CalDeliveryPath(validateOrder.getResturantorder());
            assertThat(DronePathtest).isNotNull();
            LngLatPair Points = new LngLatPair();
            Positions Appleton = new Positions();
            Appleton.setLng(-3.186874);
            Appleton.setLat(55.944494);

            Points.setPos1(Appleton);
            Points.setPos2(DronePathtest.getFlightPath()[DronePathtest.getFlightPath().length - 1]);
            PosDistance distance = new PosDistance(Points);
            assertThat(distance.getDistance() < 0.00015);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
    @Test
    void TestPathGeoJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzaOrder regionRequest = mapper.readValue(Json, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(regionRequest);
            CalDeliveryPath DronePathtest = new CalDeliveryPath(validateOrder.getResturantorder());
            assertThat(DronePathtest.toGeoJson()).isNotNull();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
