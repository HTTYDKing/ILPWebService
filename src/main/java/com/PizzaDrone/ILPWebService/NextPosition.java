package com.PizzaDrone.ILPWebService;

import com.PizzaDrone.ILPWebService.dataType.LngLatAng;
import com.PizzaDrone.ILPWebService.dataType.Positions;

public class NextPosition {

    private LngLatAng start;
    private Positions nextposition;

    public NextPosition(LngLatAng startpos) {

        double newlat, newlng;
        Positions nextpositions = new Positions();

        //Uses Math to calculate the values of the next positions
        newlat = Math.sin(Math.toRadians(startpos.getAngle()))*0.00015 + startpos.getPos().getLat();
        newlng = Math.cos(Math.toRadians(startpos.getAngle()))*0.00015 + startpos.getPos().getLng();

        nextpositions.setLng(newlng);
        nextpositions.setLat(newlat);


        this.nextposition = nextpositions;

        this.start = startpos;
    }

    // Returns the Next position
    public Positions getNextposition() {
        return nextposition;
    }

}
