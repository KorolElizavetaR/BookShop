package com.bookshop.oz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationPointDTOShops {
    private String locationId;
    private String city;
    private String address;
}
