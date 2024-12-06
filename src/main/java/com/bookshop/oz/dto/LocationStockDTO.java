package com.bookshop.oz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors (chain = true)
public class LocationStockDTO {
	private String locationId;
	private String city;
	private String address;
	private Short quantity;
}
