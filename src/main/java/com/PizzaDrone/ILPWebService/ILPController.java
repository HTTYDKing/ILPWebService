package com.PizzaDrone.ILPWebService;

import com.PizzaDrone.ILPWebService.dataType.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ILPController {

    @GetMapping("/uuid")
    public ResponseEntity<Object> getUuid() {

        return new ResponseEntity<Object>("S2335450", HttpStatus.OK);
    }

    @PostMapping("/distanceTo")
    public ResponseEntity<Object> getDistanceTo(@RequestBody String body) {

        //Currently checks if any of the variables in the code has not been instantiated

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LngLatPair coordinates = objectMapper.readValue(body, LngLatPair.class);
            if (coordinates.NotValid()) {
                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
            }

            PosDistance Distance = new PosDistance(coordinates);

            return new ResponseEntity<Object>(Distance.getDistance(), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/isCloseTo")
    public ResponseEntity<Object> getIsCloseTo(@RequestBody String body) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LngLatPair coordinates = objectMapper.readValue(body, LngLatPair.class);

            if (coordinates.NotValid()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            PosDistance Distance = new PosDistance(coordinates);
            boolean close = Distance.getDistance() < 0.00015;

            return new ResponseEntity<Object>(close, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/nextPosition")
    public ResponseEntity<Object> getNextPosition(@RequestBody String body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LngLatAng point = objectMapper.readValue(body, LngLatAng.class);

            if (point.NotValid()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            NextPosition NextPosition = new NextPosition(point);

            return ResponseEntity.status(HttpStatus.OK).body(NextPosition.getNextposition());

        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/isInRegion")
    public ResponseEntity<Object> getIsInRegion(@RequestBody RegionRequest regionRequest) {

        InRegion region = new InRegion(regionRequest);

        return new ResponseEntity<Object>(region.isInRegion(),HttpStatus.OK);
    }
}

