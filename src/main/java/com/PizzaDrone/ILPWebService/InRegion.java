package com.PizzaDrone.ILPWebService;

import com.PizzaDrone.ILPWebService.dataType.Positions;
import com.PizzaDrone.ILPWebService.dataType.RegionRequest;


public class InRegion {
    private RegionRequest region;
    private boolean inRegion = false;

    public InRegion(RegionRequest region) {
        this.region = region;

        Positions regionpoint = region.getPoint();
        Positions[] regionvertices = region.getRegion().getVertices();

        CalculateInRegion(regionvertices,regionpoint);


    }

    public InRegion() {

    }

    public boolean CalculateInRegion(Positions[] vertices,Positions point) {



        for (int i = 0; i < (vertices.length - 1); i++) {
            double xi = vertices[i].getLng(), yi = vertices[i].getLat();
            double xj = vertices[i + 1].getLng(), yj = vertices[i + 1].getLat();

            boolean intersection = ((yi > point.getLat()) != (yj > point.getLat()))
                    && (point.getLng() < (xj - xi) * (point.getLat() - yi) / (yj - yi) + xi);

            if (intersection) {
                inRegion = !inRegion;
            }
        }

        for (Positions value : vertices) {
            if ((value.getLat() == point.getLat())&&(value.getLng() == point.getLng())) {
                this.inRegion = true;
                return true;

            }
        }

        for (int i = 0; i < (vertices.length - 1); i++) {
            double x0 = (point.getLat() - vertices[i].getLat()), y0 = (point.getLng() - vertices[i].getLng());
            double x1 = (point.getLat()- vertices[i + 1].getLat()), y1 = (point.getLng() - vertices[i + 1].getLng());

            double crossProduct = x0 * y1 - x1 * y0;

            if (Math.abs(crossProduct) <= 1e-9) {
                this.inRegion = true;
                return true;
            }

        }
        return this.inRegion;
    }

    public boolean isInRegion() {
        return inRegion;
    }

}
