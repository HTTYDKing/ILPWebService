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

        this.nextposition.setLat(Math.sin(startpos.getAngle())*0.00015 + startpos.getPos().getLat());
        this.nextposition.setLng(Math.cos(startpos.getAngle())*0.00015 + startpos.getPos().getLng());

        this.start = startpos;
    }

    public LngLatAng getStart() {
        return start;
    }

    public Positions getNextposition() {
        return nextposition;
    }

}
