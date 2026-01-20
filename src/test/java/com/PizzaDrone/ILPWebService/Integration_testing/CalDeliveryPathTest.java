package com.PizzaDrone.ILPWebService.Integration_testing;

import com.PizzaDrone.ILPWebService.CalDeliveryPath;
import com.PizzaDrone.ILPWebService.PosDistance;
import com.PizzaDrone.ILPWebService.ValidateOrder;
import com.PizzaDrone.ILPWebService.dataType.LngLatPair;
import com.PizzaDrone.ILPWebService.dataType.OrderStatus;
import com.PizzaDrone.ILPWebService.dataType.PizzaOrder;
import com.PizzaDrone.ILPWebService.dataType.Positions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void TestPathOrder() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("ValidateOrder/valid_order.json")) {

            if (is == null) {
                throw new IllegalStateException("Test JSON file not found");
            }

            PizzaOrder order = mapper.readValue(is, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(order);

            assertEquals(OrderStatus.VALID, validateOrder.getStatus());
        }

    }

    @Test
    void TestPathWithinDistance() {
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
    void TestPathFollowdistanceConstraints() throws Exception{
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzaOrder regionRequest = mapper.readValue(Json, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(regionRequest);
            CalDeliveryPath DronePathtest = new CalDeliveryPath(validateOrder.getResturantorder());
            LngLatPair Points = new LngLatPair();

            for (int i = 0; i < (DronePathtest.getFlightPath().length - 1); i++) {
                Points.setPos1(DronePathtest.getFlightPath()[i]);
                Points.setPos2(DronePathtest.getFlightPath()[i + 1]);
                PosDistance distance = new PosDistance(Points);
                if ((Math.round(distance.getDistance() * 1e6) / 1e6) != 0.00015) {

                    assertEquals(0.00015, distance.getDistance(),1e-6);
                }
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    static final List<Double> ALLOWED_ANGLES = List.of(
            0.0, 22.5, 45.0, 67.5, 90.0, 112.5, 135.0, 157.5,
            180.0, 202.5, 225.0, 247.5, 270.0, 292.5, 315.0, 337.5
    );

    @Test
    void TestPathFollowableConstraints() throws Exception{
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzaOrder regionRequest = mapper.readValue(Json, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(regionRequest);
            CalDeliveryPath DronePathtest = new CalDeliveryPath(validateOrder.getResturantorder());

            for (int i = 0; i < (DronePathtest.getFlightPath().length - 1); i++) {

                double dx = DronePathtest.getFlightPath()[i + 1].getLng() - DronePathtest.getFlightPath()[i].getLng();
                double dy = DronePathtest.getFlightPath()[i + 1].getLat() - DronePathtest.getFlightPath()[i].getLat();
                double bearing = (Math.toDegrees(Math.atan2(dy, dx)) + 360) % 360;


                assertTrue(ALLOWED_ANGLES.stream().anyMatch(a -> Math.abs(a - bearing) < 1e-6));
            }

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
