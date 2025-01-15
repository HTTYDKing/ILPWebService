package com.PizzaDrone.ILPWebService;

import com.PizzaDrone.ILPWebService.dataType.OrderStatus;
import com.PizzaDrone.ILPWebService.dataType.OrderValidationCode;
import com.PizzaDrone.ILPWebService.dataType.PizzaOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ValidateOrderTest {
    @Test
    void TestValidOrder(){
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
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzaOrder regionRequest = mapper.readValue(Json, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(regionRequest);
            assertThat(validateOrder.getStatus() == OrderStatus.VALID);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    void TestInvalidCardNumber(){
        String Json = "{\n" +
                "    \"orderNo\": \"1D9EAA24\",\n" +
                "    \"orderDate\": \"2025-01-15\",\n" +
                "    \"priceTotalInPence\": 2400,\n" +
                "    \"pizzasInOrder\": [\n" +
                "      {\n" +
                "        \"name\": \"R4: Proper Pizza\",\n" +
                "        \"priceInPence\": 1400\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"R4: Pineapple & Ham & Cheese\",\n" +
                "        \"priceInPence\": 900\n" +
                "      }\n" +
                "    ],\n" +
                "    \"creditCardInformation\": {\n" +
                "      \"creditCardNumber\": \"372\",\n" +
                "      \"creditCardExpiry\": \"11/25\",\n" +
                "      \"cvv\": \"439\"\n" +
                "    }\n" +
                "  }";
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzaOrder regionRequest = mapper.readValue(Json, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(regionRequest);
            assertThat((validateOrder.getStatus() == OrderStatus.INVALID)&&
                    (validateOrder.getCode() == OrderValidationCode.CARD_NUMBER_INVALID));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    void TestInvalidExpiry(){
        String Json = "{\n" +
                "    \"orderNo\": \"7514CCFA\",\n" +
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
                "      \"creditCardNumber\": \"5541185510778347\",\n" +
                "      \"creditCardExpiry\": \"19/07\",\n" +
                "      \"cvv\": \"651\"\n" +
                "    }\n" +
                "  }";
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzaOrder regionRequest = mapper.readValue(Json, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(regionRequest);
            assertThat((validateOrder.getStatus() == OrderStatus.INVALID)&&
                    (validateOrder.getCode() == OrderValidationCode.EXPIRY_DATE_INVALID));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    void TestInvalidCVV(){
        String Json = "{\n" +
                "    \"orderNo\": \"1DA3802B\",\n" +
                "    \"orderDate\": \"2025-01-15\",\n" +
                "    \"priceTotalInPence\": 2400,\n" +
                "    \"pizzasInOrder\": [\n" +
                "      {\n" +
                "        \"name\": \"R4: Proper Pizza\",\n" +
                "        \"priceInPence\": 1400\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"R4: Pineapple & Ham & Cheese\",\n" +
                "        \"priceInPence\": 900\n" +
                "      }\n" +
                "    ],\n" +
                "    \"creditCardInformation\": {\n" +
                "      \"creditCardNumber\": \"4716078750026083\",\n" +
                "      \"creditCardExpiry\": \"03/25\",\n" +
                "      \"cvv\": \"2021\"\n" +
                "    }\n" +
                "  }";
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzaOrder regionRequest = mapper.readValue(Json, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(regionRequest);
            assertThat((validateOrder.getStatus() == OrderStatus.INVALID)&&
                    (validateOrder.getCode() == OrderValidationCode.CVV_INVALID));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    void TestInvalidTotal(){
        String Json = "{\n" +
                "    \"orderNo\": \"1005250A\",\n" +
                "    \"orderDate\": \"2025-01-15\",\n" +
                "    \"priceTotalInPence\": 2745,\n" +
                "    \"pizzasInOrder\": [\n" +
                "      {\n" +
                "        \"name\": \"R4: Proper Pizza\",\n" +
                "        \"priceInPence\": 1400\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"R4: Pineapple & Ham & Cheese\",\n" +
                "        \"priceInPence\": 900\n" +
                "      }\n" +
                "    ],\n" +
                "    \"creditCardInformation\": {\n" +
                "      \"creditCardNumber\": \"5461978840455883\",\n" +
                "      \"creditCardExpiry\": \"11/25\",\n" +
                "      \"cvv\": \"311\"\n" +
                "    }\n" +
                "  }";
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzaOrder regionRequest = mapper.readValue(Json, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(regionRequest);
            assertThat((validateOrder.getStatus() == OrderStatus.INVALID)&&
                    (validateOrder.getCode() == OrderValidationCode.TOTAL_INCORRECT));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    void TestInvalidPizza(){
        String Json = "{\n" +
                "    \"orderNo\": \"607D5EB6\",\n" +
                "    \"orderDate\": \"2025-01-15\",\n" +
                "    \"priceTotalInPence\": 498705480,\n" +
                "    \"pizzasInOrder\": [\n" +
                "      {\n" +
                "        \"name\": \"R2: Meat Lover\",\n" +
                "        \"priceInPence\": 1400\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"R2: Vegan Delight\",\n" +
                "        \"priceInPence\": 1100\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"Pizza-Surprise \",\n" +
                "        \"priceInPence\": 498702880\n" +
                "      }\n" +
                "    ],\n" +
                "    \"creditCardInformation\": {\n" +
                "      \"creditCardNumber\": \"4494248618658058\",\n" +
                "      \"creditCardExpiry\": \"03/26\",\n" +
                "      \"cvv\": \"518\"\n" +
                "    }\n" +
                "  }";
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzaOrder regionRequest = mapper.readValue(Json, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(regionRequest);
            assertThat((validateOrder.getStatus() == OrderStatus.INVALID)&&
                    (validateOrder.getCode() == OrderValidationCode.PIZZA_NOT_DEFINED));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    void TestInvalidMaxCount(){
        String Json = "{\n" +
                "    \"orderNo\": \"084E2D86\",\n" +
                "    \"orderDate\": \"2025-01-15\",\n" +
                "    \"priceTotalInPence\": 8200,\n" +
                "    \"pizzasInOrder\": [\n" +
                "      {\n" +
                "        \"name\": \"R2: Meat Lover\",\n" +
                "        \"priceInPence\": 1400\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"R2: Vegan Delight\",\n" +
                "        \"priceInPence\": 1100\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"R2: Meat Lover\",\n" +
                "        \"priceInPence\": 1400\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"R2: Meat Lover\",\n" +
                "        \"priceInPence\": 1400\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"R2: Meat Lover\",\n" +
                "        \"priceInPence\": 1400\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"R2: Meat Lover\",\n" +
                "        \"priceInPence\": 1400\n" +
                "      }\n" +
                "    ],\n" +
                "    \"creditCardInformation\": {\n" +
                "      \"creditCardNumber\": \"5446766925177799\",\n" +
                "      \"creditCardExpiry\": \"04/25\",\n" +
                "      \"cvv\": \"699\"\n" +
                "    }\n" +
                "  }";
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzaOrder regionRequest = mapper.readValue(Json, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(regionRequest);
            assertThat((validateOrder.getStatus() == OrderStatus.INVALID)&&
                    (validateOrder.getCode() == OrderValidationCode.MAX_PIZZA_COUNT_EXCEEDED));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    void TestInvalidMultipleRestaurants(){
        String Json = "{\n" +
                "    \"orderNo\": \"27FDB3C6\",\n" +
                "    \"orderDate\": \"2025-01-15\",\n" +
                "    \"priceTotalInPence\": 3800,\n" +
                "    \"pizzasInOrder\": [\n" +
                "      {\n" +
                "        \"name\": \"R4: Proper Pizza\",\n" +
                "        \"priceInPence\": 1400\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"R4: Pineapple & Ham & Cheese\",\n" +
                "        \"priceInPence\": 900\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"R2: Meat Lover\",\n" +
                "        \"priceInPence\": 1400\n" +
                "      }\n" +
                "    ],\n" +
                "    \"creditCardInformation\": {\n" +
                "      \"creditCardNumber\": \"4693216294063926\",\n" +
                "      \"creditCardExpiry\": \"02/25\",\n" +
                "      \"cvv\": \"551\"\n" +
                "    }\n" +
                "  }";
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzaOrder regionRequest = mapper.readValue(Json, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(regionRequest);
            assertThat((validateOrder.getStatus() == OrderStatus.INVALID)&&
                    (validateOrder.getCode() == OrderValidationCode.PIZZA_FROM_MULTIPLE_RESTAURANTS));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    void TestInvalidClosed(){
        String Json = "{\n" +
                "    \"orderNo\": \"445F4079\",\n" +
                "    \"orderDate\": \"2025-01-15\",\n" +
                "    \"priceTotalInPence\": 1100,\n" +
                "    \"pizzasInOrder\": [\n" +
                "      {\n" +
                "        \"name\": \"R1: Margarita\",\n" +
                "        \"priceInPence\": 1000\n" +
                "      }\n" +
                "    ],\n" +
                "    \"creditCardInformation\": {\n" +
                "      \"creditCardNumber\": \"4415432906022740\",\n" +
                "      \"creditCardExpiry\": \"11/25\",\n" +
                "      \"cvv\": \"350\"\n" +
                "    }\n" +
                "  }";
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzaOrder regionRequest = mapper.readValue(Json, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(regionRequest);
            assertThat((validateOrder.getStatus() == OrderStatus.INVALID)&&
                    (validateOrder.getCode() == OrderValidationCode.RESTAURANT_CLOSED));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    void TestInvalidPizzaPrice(){
        String Json = "{\n" +
                "    \"orderNo\": \"63040C66\",\n" +
                "    \"orderDate\": \"2025-01-15\",\n" +
                "    \"priceTotalInPence\": 2500,\n" +
                "    \"pizzasInOrder\": [\n" +
                "      {\n" +
                "        \"name\": \"R4: Pineapple & Ham & Cheese\",\n" +
                "        \"priceInPence\": 900\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"R4: Proper Pizza\",\n" +
                "        \"priceInPence\": 1500\n" +
                "      }\n" +
                "    ],\n" +
                "    \"creditCardInformation\": {\n" +
                "      \"creditCardNumber\": \"5417698609828022\",\n" +
                "      \"creditCardExpiry\": \"02/26\",\n" +
                "      \"cvv\": \"874\"\n" +
                "    }\n" +
                "  }";
        try {
            ObjectMapper mapper = new ObjectMapper();
            PizzaOrder regionRequest = mapper.readValue(Json, PizzaOrder.class);
            ValidateOrder validateOrder = new ValidateOrder(regionRequest);
            assertThat((validateOrder.getStatus() == OrderStatus.INVALID)&&
                    (validateOrder.getCode() == OrderValidationCode.PRICE_FOR_PIZZA_INVALID));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
