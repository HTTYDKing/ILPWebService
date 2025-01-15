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

    public CalDeliveryPath(Resturant resturantorder) {
        //Setting the endpoint
        Appleton.setLng(-3.186874);
        Appleton.setLat(55.944494);
        //Loading noFlyZones from websites
        String urlNoflyZone = "https://ilp-rest-2024.azurewebsites.net/noFlyZones";
        this.Noflyzone = restTemplate.getForObject(urlNoflyZone, RegionArea[].class);
        //Calling the find Path function
        this.FlightPath = findPath(resturantorder.getLocation());
    }


    public Positions[] findPath(Positions start){
        //Making a priority Queue and Hashmap and setting the priority to be the fscore
        PriorityQueue<Node> openPath = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fscore));
        Map<Positions, Node> allNodes = new HashMap<>();

        //Making the starting node and adding it to the list
        Node startNode = new Node(start, null, 0,heuristictoAppleton(start));
        openPath.add(startNode);
        allNodes.put(start,startNode);

        //Looping and comparing it to the adjacent nodes and their f and gscores
        while(!openPath.isEmpty()){
            //Getting the Top Node from the queue
            Node currentNode = openPath.poll();
            //remaking the Node to get the path and the check being if it is close to the end node
            if(heuristictoAppleton(currentNode.position)<0.00015){
                return remakePath(currentNode);
            }
            //Loops through all the valid angles
            for (double angle = 0; angle < 360 ; angle+=22.5){
                //Gets the next position
                LngLatAng nextpos = new LngLatAng();
                nextpos.setAngle(angle);
                nextpos.setPos(currentNode.position);
                NextPosition neighbourpos = new NextPosition(nextpos);
                //Checks if it is in a restricted region and skips that point if it is
                if (isinRestrictedRegion(neighbourpos.getNextposition())){
                    continue;
                }
                //Getting the gscore and adding it to the queue
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
        //Loops to check if it is in the restricted region
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
        //Gets the distance from the appleton to the position
        return Math.sqrt((Math.pow((positions.getLat()- this.Appleton.getLat()), 2)+Math.pow((positions.getLng()- this.Appleton.getLng()), 2)));
    }

    public Positions[] remakePath (Node start){
        //Remaking the path from the current node
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

    public ObjectNode toGeoJson(){
        //Converting the flight path into a GeoJson format
        ObjectMapper GeoJsonmapper = new ObjectMapper();
        //Adding the featureCollection and sections
        ObjectNode GeoJson = GeoJsonmapper.createObjectNode();
        GeoJson.put("type", "FeatureCollection");

        ArrayNode features = GeoJsonmapper.createArrayNode();
        ObjectNode feature = GeoJsonmapper.createObjectNode();
        feature.put("type", "Feature");

        ObjectNode geometry = GeoJsonmapper.createObjectNode();
        geometry.put("type", "LineString");
        ObjectNode properties = GeoJsonmapper.createObjectNode();
        ArrayNode coordinates = GeoJsonmapper.createArrayNode();
        //Adds each point in the flight path
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

        return GeoJson;
    }
}

