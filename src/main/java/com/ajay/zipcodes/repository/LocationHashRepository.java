package com.ajay.zipcodes.repository;

import com.ajay.zipcodes.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class LocationHashRepository {
    HashOperations hashOperations;

    public LocationHashRepository(RedisTemplate redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public  void set(Location location)
    {
        hashOperations.put("Location"+":"+location.getPostalcode(),location.getAbbrevation()+"."+location.getPostalcode(),location);
    }

    public Location get(String country,String postalcode)
    {
       Location location= (Location) hashOperations.get("Location"+":"+postalcode,country+"."+postalcode);
       return  location;
    }
}
