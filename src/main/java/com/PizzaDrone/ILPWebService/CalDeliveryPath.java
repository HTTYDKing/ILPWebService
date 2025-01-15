package com.PizzaDrone.ILPWebService;


import com.PizzaDrone.ILPWebService.dataType.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class CalDeliveryPath {

    private RegionArea[] Noflyzone;
    private Positions[] FlightPath;
    private final RestTemplate restTemplate = new RestTemplate();
    private final Positions Appleton = new Positions();

    public CalDeliveryPath(PizzaOrder order, Resturant resturantorder) {

        Appleton.setLng(-3.186874);
        Appleton.setLat(55.944494);

        String urlNoflyZone = "https://ilp-rest-2024.azurewebsites.net/noFlyZones";
        this.Noflyzone = restTemplate.getForObject(urlNoflyZone, RegionArea[].class);

        this.FlightPath = findPath(resturantorder.getLocation());
    }


    public Positions[] findPath(Positions start){
        PriorityQueue<Node> openPath = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fscore));
        Map<Positions, Node> allNodes = new HashMap<>();

        Node startNode = new Node(start, null, 0,heuristictoAppleton(start));
        openPath.add(startNode);
        allNodes.put(start,startNode);


        while(!openPath.isEmpty()){
            Node currentNode = openPath.poll();

            if(heuristictoAppleton(currentNode.position)<0.00015){
                return remakePath(currentNode);
            }

            for (double angle = 0; angle < 360 ; angle+=22.5){
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
                    neighbournode.fscore = Tempgscore + heuristictoAppleton(neighbourpos.getNextposition());
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

        InRegion region = new InRegion();

        for (RegionArea Region : this.Noflyzone) {
            boolean TempVar = region.CalculateInRegion(Region.getVertices(), positions);
            if (TempVar) {
                return true;
            }
        }
        return false;
    }

    public double heuristictoAppleton(Positions positions){

        return Math.sqrt((Math.pow((positions.getLat()- this.Appleton.getLat()), 2)+Math.pow((positions.getLng()- this.Appleton.getLng()), 2)));
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

    public String toGeoJson(){

        String GeoJsonString = "";
        if (this.FlightPath == null){
            return GeoJsonString;
        }
        ObjectMapper GeoJsonmapper = new ObjectMapper();

        ObjectNode GeoJson = GeoJsonmapper.createObjectNode();
        GeoJson.put("type", "FeatureCollection");

        ArrayNode features = GeoJsonmapper.createArrayNode();
        ObjectNode feature = GeoJsonmapper.createObjectNode();
        feature.put("type", "Feature");

        ObjectNode geometry = GeoJsonmapper.createObjectNode();
        geometry.put("type", "LineString");
        ObjectNode properties = GeoJsonmapper.createObjectNode();
        ArrayNode coordinates = GeoJsonmapper.createArrayNode();
        for(Positions point : this.FlightPath){
            ArrayNode pos = GeoJsonmapper.createArrayNode();
            pos.add(point.getLng());
            pos.add(point.getLat());
            coordinates.add(pos);
        }

        geometry.set("coordinates", coordinates);
        feature.set("properties",properties);
        feature.set("geometry", geometry);

        features.add(feature);


        GeoJson.set("features", features);
        try {
            GeoJsonString = GeoJsonmapper.writerWithDefaultPrettyPrinter().writeValueAsString(GeoJson);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return GeoJsonString;
    }
}

