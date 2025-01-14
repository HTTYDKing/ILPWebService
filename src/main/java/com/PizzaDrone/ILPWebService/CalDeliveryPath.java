package com.PizzaDrone.ILPWebService;


import com.PizzaDrone.ILPWebService.dataType.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class CalDeliveryPath {

    private RegionArea[] Noflyzone;
    private List<InRegion> NoflyzoneInRegion = new ArrayList<InRegion>();
    private RegionArea Central;
    private InRegion CentralInRegion;
    private Positions[] FlightPath;
    private final RestTemplate restTemplate = new RestTemplate();

    public CalDeliveryPath(PizzaOrder order, Resturant resturantorder) {

        String urlNoflyZone = "https://ilp-rest-2024.azurewebsites.net/noFlyZones";
        this.Noflyzone = restTemplate.getForObject(urlNoflyZone, RegionArea[].class);

        String urlCentral = "https://ilp-rest-2024.azurewebsites.net/centralArea";
        this.Central = restTemplate.getForObject(urlCentral, RegionArea.class);

        for (RegionArea area : Noflyzone) {
            RegionRequest regionRequest = new RegionRequest();
            regionRequest.setRegion(area);
            regionRequest.setPoint(resturantorder.getLocation());

            InRegion inRegion = new InRegion(regionRequest);
            this.NoflyzoneInRegion.add(inRegion);
        }

        RegionRequest regionRequest = new RegionRequest();
        regionRequest.setRegion(this.Central);
        regionRequest.setPoint(resturantorder.getLocation());

        InRegion inRegion = new InRegion(regionRequest);
        this.CentralInRegion = inRegion;



        this.FlightPath = findPath(resturantorder.getLocation());

    }

    public Positions[] findPath(Positions start){
        PriorityQueue<Node> openPath = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fscore));
        Map<Positions, Node> allNodes = new HashMap<>();

        Node startNode = new Node(start, null, 0,heuristictoCentral(start));

        while(!openPath.isEmpty()){
            Node currentNode = openPath.poll();

            this.CentralInRegion.CalculateInRegion(currentNode.position);
            if(this.CentralInRegion.isInRegion()){
                return remakePath(currentNode);
                }

            for (int angle = 0; angle < 360 ; angle+=90){
                LngLatAng nextpos = new LngLatAng();
                nextpos.setAngle(angle);
                nextpos.setPos(currentNode.position);
                NextPosition neighbourpos = new NextPosition(nextpos);

                if (isinRestrictedRegion(neighbourpos.getNextposition())){
                    continue;
                }

                LngLatPair Distance = new LngLatPair();
                Distance.setPos1(currentNode.position);
                Distance.setPos2(neighbourpos.getNextposition());
                PosDistance posDistance = new PosDistance(Distance);
                double Tempgscore = currentNode.gscore + posDistance.getDistance();
                Node neighbournode = allNodes.getOrDefault(neighbourpos.getNextposition(), new Node(neighbourpos.getNextposition()));

                if(Tempgscore < neighbournode.gscore){
                    neighbournode.parent = currentNode;
                    neighbournode.gscore = Tempgscore;
                    neighbournode.fscore = Tempgscore + heuristictoCentral(neighbourpos.getNextposition());
                    allNodes.put(neighbourpos.getNextposition(), neighbournode);

                    if(!openPath.contains(neighbournode)){
                        openPath.add(neighbournode);
                    }
                }
            }

        }

        return null;
    }


    public boolean isinRestrictedRegion(Positions positions){
        for (InRegion inRegion : NoflyzoneInRegion ) {
            inRegion.CalculateInRegion(positions);
            boolean TempVar = inRegion.isInRegion();
            if (TempVar) {
                return true;
            }
        }
        return false;
    }

    public double heuristictoCentral(Positions positions){
        double mindistance = Double.MAX_VALUE;

        LngLatPair Distance = new LngLatPair();

        Distance.setPos1(positions);

        for(Positions vertex : this.Central.getVertices()){
            Distance.setPos2(vertex);
            PosDistance posDistance = new PosDistance(Distance);

            if (posDistance.getDistance() < mindistance){
                mindistance = posDistance.getDistance();
            }
        }

        return mindistance;
    }

    public Positions[] remakePath (Node start){
        List<Positions> path = new ArrayList<>();
        while (start != null) {
            path.add(start.position);
            start = start.parent;
        }
        Collections.reverse(path);
        return path.toArray(new Positions[0]);
    }

    public Positions[] getFlightPath(){
        return this.FlightPath;
    }

}

