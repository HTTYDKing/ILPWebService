package com.PizzaDrone.ILPWebService.Unit_testing;

import com.PizzaDrone.ILPWebService.ValidateOrder;
import com.PizzaDrone.ILPWebService.dataType.OrderStatus;
import com.PizzaDrone.ILPWebService.dataType.PizzaOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PosDistanceTest {
//    @Test
//    void TestValidPosDistance() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//
//        try (InputStream is = getClass()
//                .getClassLoader()
//                .getResourceAsStream("ValidateOrder/valid_order.json")) {
//
//            if (is == null) {
//                throw new IllegalStateException("Test JSON file not found");
//            }
//
//            PizzaOrder order = mapper.readValue(is, PizzaOrder.class);
//            ValidateOrder validateOrder = new ValidateOrder(order);
//
//            assertEquals(OrderStatus.VALID, validateOrder.getStatus());
//        }
//    }
}
