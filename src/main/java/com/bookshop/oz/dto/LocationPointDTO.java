package com.bookshop.oz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationPointDTO {
    private String locationId;
    private String city;
    private String address;
    private Boolean isStorage = false;
}
