package com.PizzaDrone.ILPWebService;

import com.PizzaDrone.ILPWebService.dataType.Positions;
import com.PizzaDrone.ILPWebService.dataType.RegionRequest;


public class InRegion {
    private RegionRequest region;
    private boolean inRegion = false;

    //Constructor that calls and performs the calculation to determine if it is inRegion
    public InRegion(RegionRequest region) {
        this.region = region;

        Positions RegionPoint = region.getPoint();
        Positions[] RegionVertices = region.getRegion().getVertices();

        CalculateInRegion(RegionVertices,RegionPoint);

    }

    //Constructor for when the Object is needed and the individual calculation is needed
    public InRegion() {

    }

    public boolean CalculateInRegion(Positions[] vertices,Positions point) {
        // Checking if the point is to the left of an edge
        for (int i = 0; i < (vertices.length - 1); i++) {
            double xi = vertices[i].getLng(), yi = vertices[i].getLat();
            double xj = vertices[i + 1].getLng(), yj = vertices[i + 1].getLat();

            boolean intersection = ((yi > point.getLat()) != (yj > point.getLat()))
                    && (point.getLng() < (xj - xi) * (point.getLat() - yi) / (yj - yi) + xi);

            if (intersection) {
                inRegion = !inRegion;
            }
        }
        //Checking if the point is on the line region
        for (Positions value : vertices) {
            if ((value.getLat() == point.getLat())&&(value.getLng() == point.getLng())) {
                this.inRegion = true;
                return true;

            }
        }
        //Checking if it is one of the points itself
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

    // Returns the boolean of whether it is in the Region
    public boolean isInRegion() {
        return inRegion;
    }

}
