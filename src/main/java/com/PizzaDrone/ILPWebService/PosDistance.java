package com.PizzaDrone.ILPWebService;

import com.PizzaDrone.ILPWebService.dataType.LngLatPair;
import com.PizzaDrone.ILPWebService.dataType.Positions;

public class PosDistance {
    private double distance;
    private Positions position1;
    private Positions position2;

    public PosDistance(LngLatPair Points) {
        this.position1 = Points.getPos1();
        this.position2 = Points.getPos2();

        this.distance = Math.sqrt((Math.pow((position1.getLat()-position2.getLat()), 2)+Math.pow((position1.getLng()-position2.getLng()), 2)));
    }

    public double getDistance() {
        return distance;
    }
}
