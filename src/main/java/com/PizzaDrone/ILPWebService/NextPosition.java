package com.PizzaDrone.ILPWebService;

import com.PizzaDrone.ILPWebService.dataType.LngLatAng;
import com.PizzaDrone.ILPWebService.dataType.Positions;

public class NextPosition {
    private LngLatAng start;
    private Positions nextposition;

    NextPosition(LngLatAng startpos) {
        double tempangle = startpos.getAngle();
        double angledeviation;
        tempangle = tempangle % 360;
        if ((tempangle % 22.5) != 0){
            angledeviation = tempangle % 22.5;
            if (angledeviation > 11.25){
                tempangle += (22.5 - angledeviation);
            }
            else {
                tempangle += -angledeviation;
            }
        }

        startpos.setAngle(tempangle);

        double newlat, newlng;
        Positions nextpositions = new Positions();

        //Check whether it is Radian or Degrees
        newlat = Math.sin(startpos.getAngle())*0.00015 + startpos.getPos().getLat();
        newlng = Math.cos(startpos.getAngle())*0.00015 + startpos.getPos().getLng();

        nextpositions.setLat(newlat);
        nextpositions.setLng(newlng);

        this.nextposition = nextpositions;

        this.start = startpos;
    }

    public LngLatAng getStart() {
        return start;
    }

    public Positions getNextposition() {
        return nextposition;
    }

}
