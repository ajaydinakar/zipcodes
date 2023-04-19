package com.ajay.zipcodes.repository;

import com.ajay.zipcodes.model.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  LocationRepository extends CrudRepository<Location,String> {
}
