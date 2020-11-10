package com.podium.model.other;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceLocalization {

    private String city;
    private String street;
    private int buildingNumber;
    private String postalCode;
    private String localizationRemarks;

}
