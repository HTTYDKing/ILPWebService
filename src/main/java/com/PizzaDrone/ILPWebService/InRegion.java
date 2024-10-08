package com.PizzaDrone.ILPWebService;

import com.PizzaDrone.ILPWebService.dataType.Positions;
import com.PizzaDrone.ILPWebService.dataType.RegionRequest;


public class InRegion {
    private RegionRequest region;
    private boolean inRegion = false;

    public InRegion(RegionRequest region) {
        this.region = region;

        Positions point = region.getPoint();

        Positions[] verticies = region.getRegion().getVertices();

        for (int i = 0; i < (verticies.length - 1); i++) {
            double xi = verticies[i].getLng(), yi = verticies[i].getLat();
            double xj = verticies[i + 1].getLng(), yj = verticies[i + 1].getLat();



            boolean intersection = ((yi > point.getLat()) != (yj > point.getLat()))
                    && (point.getLng() < (xj - xi) * (point.getLat() - yi) / (yj - yi) + xi);

            if (intersection) {
                inRegion = !inRegion;
            }
        }
    }

    public boolean isInRegion() {
        return inRegion;
    }
}
