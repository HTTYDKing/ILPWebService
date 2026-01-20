package com.PizzaDrone.ILPWebService.Unit_testing;

import com.PizzaDrone.ILPWebService.NextPosition;
import com.PizzaDrone.ILPWebService.PosDistance;
import com.PizzaDrone.ILPWebService.dataType.LngLatAng;
import com.PizzaDrone.ILPWebService.dataType.LngLatPair;
import com.PizzaDrone.ILPWebService.dataType.Positions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NextPositionTest {

    private static final double DELTA = 1e-6;
    @Test
    public void NextPositionTest()
    {
        LngLatAng nextpoint = new LngLatAng();


        Positions p1 = new Positions();
        p1.setLng(0.0);
        p1.setLat(0.0);

        Positions p2 = new Positions();
        p2.setLng(0.00015);
        p2.setLat(0.0);


        nextpoint.setPos(p1);
        nextpoint.setAngle(0);

        NextPosition point = new NextPosition(nextpoint);

        assertEquals(p2.getLat(), point.getNextposition().getLat(), DELTA);
        assertEquals(p2.getLng(),point.getNextposition().getLng(), DELTA);
    }

    @Test
    public void NextPositionNorthTest() {

        LngLatAng nextpoint = new LngLatAng();

        Positions p1 = new Positions();
        p1.setLng(0.0);
        p1.setLat(0.0);

        Positions p2 = new Positions();
        p2.setLng(0.0);
        p2.setLat(0.00015);

        nextpoint.setPos(p1);
        nextpoint.setAngle(90);

        NextPosition point = new NextPosition(nextpoint);

        assertEquals(p2.getLat(), point.getNextposition().getLat(), DELTA);
        assertEquals(p2.getLng(), point.getNextposition().getLng(), DELTA);
    }

    @Test
    public void NextPositionWestTest() {

        LngLatAng nextpoint = new LngLatAng();

        Positions p1 = new Positions();
        p1.setLng(0.0);
        p1.setLat(0.0);

        Positions p2 = new Positions();
        p2.setLng(-0.00015);
        p2.setLat(0.0);

        nextpoint.setPos(p1);
        nextpoint.setAngle(180);

        NextPosition point = new NextPosition(nextpoint);

        assertEquals(p2.getLat(), point.getNextposition().getLat(), DELTA);
        assertEquals(p2.getLng(), point.getNextposition().getLng(), DELTA);
    }

    @Test
    public void NextPositionSouthTest() {

        LngLatAng nextpoint = new LngLatAng();

        Positions p1 = new Positions();
        p1.setLng(0.0);
        p1.setLat(0.0);

        Positions p2 = new Positions();
        p2.setLng(0.0);
        p2.setLat(-0.00015);

        nextpoint.setPos(p1);
        nextpoint.setAngle(270);

        NextPosition point = new NextPosition(nextpoint);

        assertEquals(p2.getLat(), point.getNextposition().getLat(), DELTA);
        assertEquals(p2.getLng(), point.getNextposition().getLng(), DELTA);
    }

    @Test
    public void NextPositionHoverTest() {

        LngLatAng nextpoint = new LngLatAng();

        Positions p1 = new Positions();
        p1.setLng(1.5);
        p1.setLat(2.5);

        nextpoint.setPos(p1);
        nextpoint.setAngle(999);

        NextPosition point = new NextPosition(nextpoint);

        assertEquals(p1.getLat(), point.getNextposition().getLat(), 1e-3);
        assertEquals(p1.getLng(), point.getNextposition().getLng(), 1e-3);
    }
}
