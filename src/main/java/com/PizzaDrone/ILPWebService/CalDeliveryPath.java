package com.PizzaDrone.ILPWebService;


import com.PizzaDrone.ILPWebService.dataType.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CalDeliveryPath {

    private RegionArea[] Noflyzone;
    private List<InRegion> NoflyzoneInRegion;
    private RegionArea Central;
    private List<Positions> FlightPath;
    private final RestTemplate restTemplate = new RestTemplate();

    public CalDeliveryPath(PizzaOrder order, Resturant resturantorder) {

        String urlNoflyZone = "https://ilp-rest-2024.azurewebsites.net/noFlyZones";
        this.Noflyzone = restTemplate.getForObject(urlNoflyZone, RegionArea[].class);

        String urlCentral = "https://ilp-rest-2024.azurewebsites.net/centralArea";
        this.Central = restTemplate.getForObject(urlCentral, RegionArea.class);

        for (RegionArea area : Noflyzone) {
            RegionRequest regionRequest = new RegionRequest();
            regionRequest.setRegion(area);
            regionRequest.setPoint(resturantorder.getLocation());

            InRegion inRegion = new InRegion(regionRequest);
            this.NoflyzoneInRegion.add(inRegion);
        }

        findPath();

    }

    public void findPath(){


    }

    public Positions getPositions(){
        return null;
    }

    public boolean isinRestrictedRegion(Positions positions){
        for (InRegion inRegion : NoflyzoneInRegion ) {
            inRegion.CalculateInRegion(positions);
            boolean TempVar = inRegion.isInRegion();
            if (TempVar) {
                return true;
            }
        }
        return false;
    }
}

