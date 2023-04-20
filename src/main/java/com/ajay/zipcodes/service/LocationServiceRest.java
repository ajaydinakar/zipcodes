package com.ajay.zipcodes.service;

import com.ajay.zipcodes.model.Location;
import com.ajay.zipcodes.repository.LocationDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class LocationServiceRest {
    @Autowired
    LocationDAO dao;
    private  RestTemplate restTemplate;
    @Value("${baseurl}")
    String baseUrl;

    public LocationServiceRest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Location getLocation(String country, String postalcode)  {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl).pathSegment(country).pathSegment(postalcode).build();
        Location location=restTemplate.getForObject(url.toString(),Location.class);
        dao.save(location);
        return  location;
    }

    public Location getLocationFromRedis(String country,String postalcode)
    {
        Location location= dao.getObj(country,postalcode);
        if(location!=null) {
            return location;
        }
        else
        {
            getLocation(country,postalcode)  ;
            location= dao.getObj(country,postalcode);
            return location;
        }
    }
}
