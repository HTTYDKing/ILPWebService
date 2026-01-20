package com.PizzaDrone.ILPWebService.Unit_testing;

import com.PizzaDrone.ILPWebService.InRegion;
import com.PizzaDrone.ILPWebService.dataType.Positions;
import com.PizzaDrone.ILPWebService.dataType.RegionArea;
import com.PizzaDrone.ILPWebService.dataType.RegionRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InRegionTest {
    @Test
    public void test_InRegionTrue() {

        Positions[] vertices = new Positions[] {
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions()
        };

        Positions point = new Positions();

        vertices[0].setLat(1); vertices[0].setLng(1);
        vertices[1].setLat(1); vertices[1].setLng(2);
        vertices[2].setLat(2); vertices[2].setLng(2);
        vertices[3].setLat(2); vertices[3].setLng(1);
        vertices[4].setLat(1); vertices[4].setLng(1);

        point.setLat(1.5);
        point.setLng(1.5);

        InRegion inRegion = new InRegion();

        assertEquals(true, inRegion.CalculateInRegion(vertices, point));
    }

    @Test
    public void test_InRegionFalse() {

        Positions[] vertices = new Positions[] {
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions()
        };

        Positions point = new Positions();

        vertices[0].setLat(1); vertices[0].setLng(1);
        vertices[1].setLat(1); vertices[1].setLng(2);
        vertices[2].setLat(2); vertices[2].setLng(2);
        vertices[3].setLat(2); vertices[3].setLng(1);
        vertices[4].setLat(1); vertices[4].setLng(1);

        point.setLat(1.5);
        point.setLng(10);

        InRegion inRegion = new InRegion();

        assertEquals(false, inRegion.CalculateInRegion(vertices, point));
    }

    @Test
    public void test_InRegionBoundary() {

        Positions[] vertices = new Positions[] {
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions()
        };

        Positions point = new Positions();

        vertices[0].setLat(1); vertices[0].setLng(1);
        vertices[1].setLat(1); vertices[1].setLng(2);
        vertices[2].setLat(2); vertices[2].setLng(2);
        vertices[3].setLat(2); vertices[3].setLng(1);
        vertices[4].setLat(1); vertices[4].setLng(1);

        point.setLat(1);
        point.setLng(1.5);

        InRegion inRegion = new InRegion();

        assertEquals(true, inRegion.CalculateInRegion(vertices, point));
    }

    @Test
    public void test_InRegionVertex() {

        Positions[] vertices = new Positions[] {
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions()
        };

        Positions point = new Positions();

        vertices[0].setLat(1); vertices[0].setLng(1);
        vertices[1].setLat(1); vertices[1].setLng(2);
        vertices[2].setLat(2); vertices[2].setLng(2);
        vertices[3].setLat(2); vertices[3].setLng(1);
        vertices[4].setLat(1); vertices[4].setLng(1);

        point.setLat(1);
        point.setLng(1);

        InRegion inRegion = new InRegion();

        assertEquals(true, inRegion.CalculateInRegion(vertices, point));
    }

    @Test
    public void test_InRegionColinearPastSegment() {

        Positions[] vertices = new Positions[] {
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions()
        };

        Positions point = new Positions();

        vertices[0].setLat(1); vertices[0].setLng(1);
        vertices[1].setLat(1); vertices[1].setLng(2);
        vertices[2].setLat(2); vertices[2].setLng(2);
        vertices[3].setLat(2); vertices[3].setLng(1);
        vertices[4].setLat(1); vertices[4].setLng(1);

        point.setLat(1);
        point.setLng(3);

        InRegion inRegion = new InRegion();

        assertEquals(false, inRegion.CalculateInRegion(vertices, point));
    }

    @Test
    public void test_InRegionTriangleTrue() {

        Positions[] vertices = new Positions[] {
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions()
        };

        Positions point = new Positions();

        vertices[0].setLat(0); vertices[0].setLng(0);
        vertices[1].setLat(0); vertices[1].setLng(2);
        vertices[2].setLat(2); vertices[2].setLng(1);
        vertices[3].setLat(0); vertices[3].setLng(0);

        point.setLat(0.5);
        point.setLng(1);

        InRegion inRegion = new InRegion();

        assertEquals(true, inRegion.CalculateInRegion(vertices, point));
    }

    @Test
    public void test_InRegionTriangleFalse() {

        Positions[] vertices = new Positions[] {
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions()
        };

        Positions point = new Positions();

        vertices[0].setLat(0); vertices[0].setLng(0);
        vertices[1].setLat(0); vertices[1].setLng(2);
        vertices[2].setLat(2); vertices[2].setLng(1);
        vertices[3].setLat(0); vertices[3].setLng(0);

        point.setLat(2);
        point.setLng(2);

        InRegion inRegion = new InRegion();

        assertEquals(false, inRegion.CalculateInRegion(vertices, point));
    }

    @Test
    public void test_InRegionConcaveTrue() {

        Positions[] vertices = new Positions[] {
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions()
        };

        Positions point = new Positions();

        vertices[0].setLat(0); vertices[0].setLng(0);
        vertices[1].setLat(0); vertices[1].setLng(3);
        vertices[2].setLat(1); vertices[2].setLng(3);
        vertices[3].setLat(1); vertices[3].setLng(1);
        vertices[4].setLat(3); vertices[4].setLng(1);
        vertices[5].setLat(3); vertices[5].setLng(0);
        vertices[6].setLat(0); vertices[6].setLng(0);

        point.setLat(2);
        point.setLng(0.5);

        InRegion inRegion = new InRegion();

        assertEquals(true, inRegion.CalculateInRegion(vertices, point));
    }

    @Test
    public void test_InRegionConcaveFalse() {

        Positions[] vertices = new Positions[] {
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions(),
                new Positions()
        };

        Positions point = new Positions();

        vertices[0].setLat(0); vertices[0].setLng(0);
        vertices[1].setLat(0); vertices[1].setLng(3);
        vertices[2].setLat(1); vertices[2].setLng(3);
        vertices[3].setLat(1); vertices[3].setLng(1);
        vertices[4].setLat(3); vertices[4].setLng(1);
        vertices[5].setLat(3); vertices[5].setLng(0);
        vertices[6].setLat(0); vertices[6].setLng(0);

        point.setLat(2);
        point.setLng(2);

        InRegion inRegion = new InRegion();

        assertEquals(false, inRegion.CalculateInRegion(vertices, point));
    }
}
