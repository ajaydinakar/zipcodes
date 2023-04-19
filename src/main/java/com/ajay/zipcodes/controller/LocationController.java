package com.ajay.zipcodes.controller;

import com.ajay.zipcodes.model.Location;
import com.ajay.zipcodes.model.Place;
import com.ajay.zipcodes.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

@RestController
@Slf4j
public class LocationController {
    @Autowired
    LocationService service;

    @GetMapping("/save/{country}/{postalcode}")
    public Location putLocation(@PathVariable("country") String country, @PathVariable("postalcode") String postalcode) {

       return   service.getLocation(country,postalcode);

//        return Location.builder().abbrevation("ind")
//                .country("India")
//                .placeList(new ArrayList<Place>())
//                .build();

    }
    @GetMapping("/get/{country}/{postalcode}")
    public Location getLocation(@PathVariable("country") String country, @PathVariable("postalcode") String postalcode) {
        log.info("llocation is ");
        return  service.getLocationFromRedis(country,postalcode);
    }

}
