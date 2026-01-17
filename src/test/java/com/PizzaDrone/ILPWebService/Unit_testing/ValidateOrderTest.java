package com.PizzaDrone.ILPWebService.Unit_testing;

import com.PizzaDrone.ILPWebService.ValidateOrder;
import com.PizzaDrone.ILPWebService.dataType.OrderStatus;
import com.PizzaDrone.ILPWebService.dataType.OrderValidationCode;
import com.PizzaDrone.ILPWebService.dataType.PizzaOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateOrderTest {
    @Test
    void TestValidOrder() throws Exception {
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
    void TestInvalidCardNumber() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("ValidateOrder/InvalidCardNumber.json")) {

            if (is == null) {
                throw new IllegalStateException("Test JSON file not found");
            }

            PizzaOrder order = mapper.readValue(is, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(order);

            assertEquals(OrderStatus.INVALID, validateOrder.getStatus());
            assertEquals(OrderValidationCode.CARD_NUMBER_INVALID,validateOrder.getCode());
        }
    }

    @Test
    void TestInvalidExpiry() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("ValidateOrder/InvalidExpiry.json")) {

            if (is == null) {
                throw new IllegalStateException("Test JSON file not found");
            }

            PizzaOrder order = mapper.readValue(is, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(order);

            assertEquals(OrderStatus.INVALID, validateOrder.getStatus());
            assertEquals(OrderValidationCode.EXPIRY_DATE_INVALID,validateOrder.getCode());
        }
    }

    @Test
    void TestInvalidCVV() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("ValidateOrder/InvalidCVV.json")) {

            if (is == null) {
                throw new IllegalStateException("Test JSON file not found");
            }

            PizzaOrder order = mapper.readValue(is, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(order);

            assertEquals(OrderStatus.INVALID, validateOrder.getStatus());
            assertEquals(OrderValidationCode.CVV_INVALID,validateOrder.getCode());
        }
    }

    @Test
    void TestInvalidTotal() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("ValidateOrder/InvalidTotal.json")) {

            if (is == null) {
                throw new IllegalStateException("Test JSON file not found");
            }

            PizzaOrder order = mapper.readValue(is, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(order);

            assertEquals(OrderStatus.INVALID, validateOrder.getStatus());
            assertEquals(OrderValidationCode.TOTAL_INCORRECT,validateOrder.getCode());
        }
    }

    @Test
    void TestInvalidPizza() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("ValidateOrder/InvalidPizza.json")) {

            if (is == null) {
                throw new IllegalStateException("Test JSON file not found");
            }

            PizzaOrder order = mapper.readValue(is, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(order);

            assertEquals(OrderStatus.INVALID, validateOrder.getStatus());
            assertEquals(OrderValidationCode.PIZZA_NOT_DEFINED,validateOrder.getCode());
        }
    }

    @Test
    void TestInvalidMaxCount() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("ValidateOrder/InvalidMaxCount.json")) {

            if (is == null) {
                throw new IllegalStateException("Test JSON file not found");
            }

            PizzaOrder order = mapper.readValue(is, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(order);

            assertEquals(OrderStatus.INVALID, validateOrder.getStatus());
            assertEquals(OrderValidationCode.MAX_PIZZA_COUNT_EXCEEDED,validateOrder.getCode());
        }
    }

    @Test
    void TestInvalidMultipleRestaurants() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("ValidateOrder/InvalidMultipleRestaurants.json")) {

            if (is == null) {
                throw new IllegalStateException("Test JSON file not found");
            }

            PizzaOrder order = mapper.readValue(is, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(order);

            assertEquals(OrderStatus.INVALID, validateOrder.getStatus());
            assertEquals(OrderValidationCode.PIZZA_FROM_MULTIPLE_RESTAURANTS,validateOrder.getCode());
        }
    }

    @Test
    void TestInvalidClosed() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("ValidateOrder/InvalidClosed.json")) {

            if (is == null) {
                throw new IllegalStateException("Test JSON file not found");
            }

            PizzaOrder order = mapper.readValue(is, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(order);

            assertEquals(OrderStatus.INVALID, validateOrder.getStatus());
            assertEquals(OrderValidationCode.RESTAURANT_CLOSED,validateOrder.getCode());
        }
    }

    @Test
    void TestInvalidPizzaPrice() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("ValidateOrder/InvalidPizzaPrice.json")) {

            if (is == null) {
                throw new IllegalStateException("Test JSON file not found");
            }

            PizzaOrder order = mapper.readValue(is, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(order);

            assertEquals(OrderStatus.INVALID, validateOrder.getStatus());
            assertEquals(OrderValidationCode.PRICE_FOR_PIZZA_INVALID,validateOrder.getCode());
        }
    }
}
