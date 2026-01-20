package com.PizzaDrone.ILPWebService.Unit_testing;

import com.PizzaDrone.ILPWebService.PosDistance;
import com.PizzaDrone.ILPWebService.ValidateOrder;
import com.PizzaDrone.ILPWebService.dataType.LngLatPair;
import com.PizzaDrone.ILPWebService.dataType.OrderStatus;
import com.PizzaDrone.ILPWebService.dataType.PizzaOrder;
import com.PizzaDrone.ILPWebService.dataType.Positions;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PosDistanceTest {

    private static final double DELTA = 1e-6;

    @Test
    void distanceIsZeroForSamePoint() {
        LngLatPair points = new LngLatPair();

        Positions p1 = new Positions();
        p1.setLng(1);
        p1.setLat(1);

        Positions p2 = new Positions();
        p2.setLng(1);
        p2.setLat(1);

        points.setPos1(p1);
        points.setPos2(p2);

        PosDistance posDistance = new PosDistance(points);

        assertEquals(0.0, posDistance.getDistance(), DELTA);
    }


    @Test
    void distanceIsSymmetric() {
        LngLatPair points1 = new LngLatPair();
        LngLatPair points2 = new LngLatPair();

        Positions a = new Positions();
        a.setLng(0);
        a.setLat(0);

        Positions b = new Positions();
        b.setLng(2);
        b.setLat(3);

        points1.setPos1(a);
        points1.setPos2(b);

        points2.setPos1(b);
        points2.setPos2(a);

        PosDistance d1 = new PosDistance(points1);
        PosDistance d2 = new PosDistance(points2);

        assertEquals(d1.getDistance(), d2.getDistance(), DELTA);
    }


    @Test
    void distanceWithLongitudeChangeOnly() {
        LngLatPair points = new LngLatPair();

        Positions p1 = new Positions();
        p1.setLng(0);
        p1.setLat(0);

        Positions p2 = new Positions();
        p2.setLng(5);
        p2.setLat(0);

        points.setPos1(p1);
        points.setPos2(p2);

        PosDistance posDistance = new PosDistance(points);

        assertEquals(5.0, posDistance.getDistance(), DELTA);
    }


    @Test
    void distanceWithLatitudeChangeOnly() {
        LngLatPair points = new LngLatPair();

        Positions p1 = new Positions();
        p1.setLng(0);
        p1.setLat(0);

        Positions p2 = new Positions();
        p2.setLng(0);
        p2.setLat(4);

        points.setPos1(p1);
        points.setPos2(p2);

        PosDistance posDistance = new PosDistance(points);

        assertEquals(4.0, posDistance.getDistance(), DELTA);
    }


    @Test
    void distanceMatchesKnownPythagoreanCase() {
        LngLatPair points = new LngLatPair();

        Positions p1 = new Positions();
        p1.setLng(0);
        p1.setLat(0);

        Positions p2 = new Positions();
        p2.setLng(3);
        p2.setLat(4);

        points.setPos1(p1);
        points.setPos2(p2);

        PosDistance posDistance = new PosDistance(points);

        assertEquals(5.0, posDistance.getDistance(), DELTA);
    }

    @Test
    void distanceHandlesSmallPrecisionValues() {
        LngLatPair points = new LngLatPair();

        Positions p1 = new Positions();
        p1.setLng(0.0);
        p1.setLat(0.0);

        Positions p2 = new Positions();
        p2.setLng(0.00015);
        p2.setLat(0.0);

        points.setPos1(p1);
        points.setPos2(p2);

        PosDistance posDistance = new PosDistance(points);

        assertEquals(0.00015, posDistance.getDistance(), 1e-12);
    }

    @Test
    void ValidPosDistance_propercomputation() {

        LngLatPair points = new LngLatPair();

        Positions p1 = new Positions();
        p1.setLng(0.0);
        p1.setLat(0.0);

        Positions p2 = new Positions();
        p2.setLng(1);
        p2.setLat(1);

        points.setPos1(p1);
        points.setPos2(p2);

        PosDistance posDistance = new PosDistance(points);

        assertEquals(posDistance.getDistance(), Math.sqrt(2), DELTA);

    }
}
