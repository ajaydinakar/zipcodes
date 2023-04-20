package com.ajay.zipcodes.service;

import com.ajay.zipcodes.model.Location;
import com.ajay.zipcodes.repository.LocationDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class LocationService {
    @Autowired
    LocationDAO dao;
    WebClient webClient;

    public LocationService(@Value("${baseurl}") String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public Location getLocation(String country,String postalcode)  {
        Location location = new Location();
        WebClient.ResponseSpec responseSpec =
                webClient
                        .get()
                        .uri(uriBuilder -> uriBuilder.path("/{country}/{postalcode}")
                                .build(country,postalcode))
                        .retrieve();
        Mono<Location> mono = responseSpec.bodyToMono(Location.class);
        location= mono.block();
        return location;
    }

    public Location getLocationFromRedis(String country,String postalcode)
    {
       Optional<Location>  location=null;
       location= dao.getObjJpa(postalcode);
        if(location.isPresent()) {
            return location.get();
        }
        else
        {
           Location location1= getLocation(country,postalcode)  ;
            dao.saveJpa(location1);
             return location1;
        }
    }

}
