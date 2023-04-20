package com.ajay.zipcodes.controller;

import com.ajay.zipcodes.model.Location;
import com.ajay.zipcodes.service.LocationService;
import com.ajay.zipcodes.service.LocationServiceRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class LocationController {
    @Autowired
    LocationService service;
    @Autowired
    LocationServiceRest serviceRest;


    @GetMapping("/location/{country}/{postalcode}")
    public Location getLocationRest(@PathVariable("country") String country, @PathVariable("postalcode") String postalcode) {
        return  serviceRest.getLocationFromRedis(country,postalcode);
    }
    // below one is non recommended approach through webclient as it is non reactive way
    @GetMapping("/locationV2/{country}/{postalcode}")
    public Location getLocation(@PathVariable("country") String country, @PathVariable("postalcode") String postalcode) {
        return  service.getLocation(country,postalcode);
    }

}
