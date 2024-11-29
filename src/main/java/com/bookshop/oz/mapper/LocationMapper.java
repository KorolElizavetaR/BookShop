package com.bookshop.oz.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.BookProductDTOBooksForMainPage;
import com.bookshop.oz.dto.LocationPointDTO;
import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.model.LocationPoint;

@Component
public class LocationMapper {
	@Autowired
	private ModelMapper modelMapper;
	
	public LocationPointDTO getLocationPointDTO(LocationPoint locationPoint) {
		return modelMapper.map(locationPoint, LocationPointDTO.class);
	}
}
