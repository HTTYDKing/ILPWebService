package com.PizzaDrone.ILPWebService;

import com.PizzaDrone.ILPWebService.dataType.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class ValidateOrder {

    private PizzaOrder order;
    private OrderStatus status;
    private OrderValidationCode code;
    private Resturant resturantorder;
    private final RestTemplate restTemplate = new RestTemplate();

    public ValidateOrder(PizzaOrder order) {
        this.order = order;
        Date ActualExpiraryDate = new Date();

        try {
            // Call the method that throws ParseException
            ActualExpiraryDate = order.getCreditCardInformation().getExpireDate();
        } catch (Exception e) {
            this.status = OrderStatus.INVALID;
            this.code = OrderValidationCode.EXPIRY_DATE_INVALID;
        }


        PizzaDetails[] Pizzas = order.getPizzasInOrder();
        double TotalPrice = 100;

        for (PizzaDetails Pizza : Pizzas) {
            TotalPrice += Pizza.getPizzaPrice();
        }

        String url = "https://ilp-rest-2024.azurewebsites.net/restaurants";
        Resturant[] restaurantArray = restTemplate.getForObject(url, Resturant[].class);


        List<String> allRestaurantPizzas = new ArrayList<>();

        assert restaurantArray != null;
        for (Resturant restaurant : restaurantArray) {
            for (PizzaDetails Pizza : restaurant.getmenu()) {
                allRestaurantPizzas.add(Pizza.getPizzaName());
            }
        }

        List <String> PizzaorderList = new ArrayList<>();
        for (PizzaDetails Pizza : Pizzas) {
            PizzaorderList.add(Pizza.getPizzaName());
        }

        boolean PizzaFromMultipleRestaurant = false;
        char Restaurantnum = Pizzas[0].getPizzaName().charAt(1);
        for (PizzaDetails Pizza : Pizzas) {
            if(Restaurantnum != Pizza.getPizzaName().charAt(1)) {
                PizzaFromMultipleRestaurant = true;
                break;
            }
        }
        this.resturantorder = restaurantArray[Integer.parseInt(String.valueOf(Restaurantnum)) - 1];

        List<String> RestuantOpeningDays = new ArrayList<>(
                Arrays.asList(restaurantArray[Integer.parseInt(String.valueOf(Restaurantnum)) - 1].openingDates()));

        String orderDay = switch (order.getOrderDate().getDay()) {
            case 0 -> "SUNDAY";
            case 1 -> "MONDAY";
            case 2 -> "TUESDAY";
            case 3 -> "WEDNESDAY";
            case 4 -> "THURSDAY";
            case 5 -> "FRIDAY";
            case 6 -> "SATURDAY";
            default -> "INVALID DAY";
        };

        List<Double> allRestaurantPizzasPrice = new ArrayList<>();

        for (Resturant restaurant : restaurantArray) {
            for (PizzaDetails Pizza : restaurant.getmenu()) {
                allRestaurantPizzasPrice.add(Pizza.getPizzaPrice());
            }
        }

        boolean PriceforPizzaInvalid = false;
        for (PizzaDetails pizza : order.getPizzasInOrder()) {
            int index = allRestaurantPizzas.indexOf(pizza.getPizzaName());
            if (pizza.getPizzaPrice() != allRestaurantPizzasPrice.get(index)) {
                PriceforPizzaInvalid = true;
                break;
            }
        }


        //Do UNDEFINED check
        if (order.getCreditCardInformation().getCardNumber().length() != 16){
            this.status = OrderStatus.INVALID;
            this.code = OrderValidationCode.CARD_NUMBER_INVALID;
        }
        else if ( order.getOrderDate().after(ActualExpiraryDate)){
            this.status = OrderStatus.INVALID;
            this.code = OrderValidationCode.EXPIRY_DATE_INVALID;
        }
        else if (order.getCreditCardInformation().getCvv().length() != 3) {
            this.status = OrderStatus.INVALID;
            this.code = OrderValidationCode.CVV_INVALID;
        }
        else if (TotalPrice != order.getPriceTotalInPence()){
            this.status = OrderStatus.INVALID;
            this.code = OrderValidationCode.TOTAL_INCORRECT;
        }
        else if (!allRestaurantPizzas.containsAll(PizzaorderList)){
            this.status = OrderStatus.INVALID;
            this.code = OrderValidationCode.PIZZA_NOT_DEFINED;
        }
        else if (order.getPizzasInOrder().length > 4){
            this.status = OrderStatus.INVALID;
            this.code = OrderValidationCode.MAX_PIZZA_COUNT_EXCEEDED;
        }
        else if (PizzaFromMultipleRestaurant){
            this.status = OrderStatus.INVALID;
            this.code = OrderValidationCode.PIZZA_FROM_MULTIPLE_RESTAURANTS;
        }
        else if (!RestuantOpeningDays.contains(orderDay)){
            this.status = OrderStatus.INVALID;
            this.code = OrderValidationCode.RESTAURANT_CLOSED;
        }
        else if (PriceforPizzaInvalid){
            this.status = OrderStatus.INVALID;
            this.code = OrderValidationCode.PRICE_FOR_PIZZA_INVALID;
        }
        else if (order.getPizzasInOrder().length == 0){
            this.status = OrderStatus.INVALID;
            this.code = OrderValidationCode.EMPTY_ORDER;
        }
        else {
            this.status = OrderStatus.VALID;
            this.code = OrderValidationCode.NO_ERROR;
        }

    }

    public OrderStatus getStatus() {
        return status;
    }
    public OrderValidationCode getCode() {
        return code;
    }

    public PizzaOrder getOrder() {
        return order;
    }

    public Resturant getResturantorder(){
        return resturantorder;
    }
}
