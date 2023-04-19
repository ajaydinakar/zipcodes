package com.ajay.zipcodes.service;
import com.ajay.zipcodes.model.Location;
import com.ajay.zipcodes.repository.LocationDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class LocationService {
    @Autowired
    RedisTemplate redisTemplate;
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
                        .retrieve()
                ;
        log.info("priority of main thread",Thread.currentThread().getPriority());
        Mono<Location> mono = responseSpec.bodyToMono(Location.class);
        //this is bad logic made this to learn how this works
        mono
                .doOnNext(response -> {
            System.out.println(response);
            location.setAbbrevation(response.getAbbrevation());
            location.setCountry(response.getCountry());
            location.setPlaces(response.getPlaces());
            location.setPostalcode(response.getPostalcode());
                    log.info("saving location info in redis");
                    dao.save(location);
                    log.info("info is saved");
        }).subscribe();
        return location;
    }

    public Location getLocationFromRedis(String country,String postalcode)
    {
        log.info("priority of main thread",Thread.currentThread().getPriority());
       Optional<Location>  location=null;
        log.info("getting location info in service");
      location= dao.getObj(postalcode);
        log.info(" location info in service is {}",location);
        if(location.isPresent()) {
            return location.get();
        }
        else
        {
            getLocation(country,postalcode)  ;
             location= dao.getObj("534342");
             return location.get();
        }
    }

}
