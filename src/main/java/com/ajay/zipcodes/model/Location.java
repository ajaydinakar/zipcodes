package com.ajay.zipcodes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("Location")
public class Location implements Serializable {
    @JsonProperty("post code")
            @Id
    String postalcode;
    String country;
    @JsonProperty("country abbreviation")
    String abbrevation;
    @JsonProperty("places")
    List<Place> places;

}
