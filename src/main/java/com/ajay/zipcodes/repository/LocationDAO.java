package com.ajay.zipcodes.repository;

import com.ajay.zipcodes.model.Location;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LocationDAO {

    @Autowired
    LocationRepository repository;

   public void save(Location location)
    {
        repository.save(location);
//        ObjectMapper oMapper = new ObjectMapper();
//        template.opsForHash().put("LOC",oMapper.convertValue(location,Location.class);
    }


    public Optional<Location> getObj(String postalcode)
    {
//       return (Location) template.opsForHash().get("LOC",postalcode);
        return repository.findById(postalcode);
    }
}
