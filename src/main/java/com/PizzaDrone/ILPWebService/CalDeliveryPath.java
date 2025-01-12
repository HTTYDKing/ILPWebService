package com.PizzaDrone.ILPWebService;


import com.PizzaDrone.ILPWebService.dataType.PizzaOrder;
import com.PizzaDrone.ILPWebService.dataType.Positions;
import com.PizzaDrone.ILPWebService.dataType.RegionArea;
import com.PizzaDrone.ILPWebService.dataType.Resturant;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CalDeliveryPath {

    private RegionArea[] Noflyzone;
    private RegionArea Central;
    private List<Positions> FlightPath;
    private final RestTemplate restTemplate = new RestTemplate();

    public CalDeliveryPath(PizzaOrder order, Resturant resturantorder) {

        String urlNoflyZone = "https://ilp-rest-2024.azurewebsites.net/noFlyZones";
        RegionArea[] Noflyzone = restTemplate.getForObject(urlNoflyZone, RegionArea[].class);

        String urlCentral = "https://ilp-rest-2024.azurewebsites.net/centralArea";
        RegionArea Central = restTemplate.getForObject(urlCentral, RegionArea.class);


    }
}

