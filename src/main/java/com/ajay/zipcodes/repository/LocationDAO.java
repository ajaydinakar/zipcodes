package com.ajay.zipcodes.repository;

import com.ajay.zipcodes.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class LocationDAO {
@Autowired
RedisTemplate<String,Object> redisTemplate;
    @Autowired
    LocationRepository redisJpaRepository;
    @Autowired
    LocationHashRepository redisHashrepository;

    // retrieving and storing data in redis through JPA repostiory
    public void saveJpa(Location location)
    {
          redisJpaRepository.save(location);
    }
    public Optional<Location> getObjJpa(String postalcode)
    {
        return redisJpaRepository.findById(postalcode);
    }

//     retriving and storing in redis through custom repository
    public Location getObj(String country, String postalcode)
    {
      return  redisHashrepository.get(country,postalcode);
    }
    public void save(Location location)
    { redisHashrepository.set(location); }

}
