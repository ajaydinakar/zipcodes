package com.ajay.zipcodes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Place implements Serializable {
    private static final long serialVersionUID = -2297165173615881318L;
    @JsonProperty("place name")
    String name;
    String longitude;
    String State;
    @JsonProperty("state abbreviation")
    String state_abbreviation;
    String latitude;
}
