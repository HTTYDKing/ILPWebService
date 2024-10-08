package com.PizzaDrone.ILPWebService;

import com.PizzaDrone.ILPWebService.dataType.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.synth.Region;


@RestController
public class ILPController {

    @GetMapping("/uuid")
    public ResponseEntity<Object> getUuid() {

        return new ResponseEntity<Object>("S2335450", HttpStatus.OK);
    }

    @PostMapping("/distanceTo")
    public ResponseEntity<Object> getDistanceTo(@RequestBody LngLatPair coordinates) {

        //Currently a place holder add the proper conditional comparison as required
        if (coordinates == null) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }

        PosDistance Distance = new PosDistance(coordinates);


        return new ResponseEntity<Object>(Distance.getDistance(), HttpStatus.OK);
    }

    @PostMapping("/isCloseTo")
    public ResponseEntity<Object> getIsCloseTo(@RequestBody LngLatPair coordinates) {

        PosDistance Distance = new PosDistance(coordinates);
        boolean close = Distance.getDistance() < 0.00015;

        return new ResponseEntity<Object>(close,HttpStatus.OK);
    }

    @PostMapping("/nextPosition")
    public ResponseEntity<Object> getNextPosition(@RequestBody LngLatAng point) {
        NextPosition NextPosition = new NextPosition(point);

        return ResponseEntity.status(HttpStatus.OK).body(NextPosition.getNextposition());
    }

    @PostMapping("/isInRegion")
    public ResponseEntity<Object> getIsInRegion(@RequestBody RegionRequest regionRequest) {

        InRegion region = new InRegion(regionRequest);

        return new ResponseEntity<Object>(region.isInRegion(),HttpStatus.OK);
    }
}

