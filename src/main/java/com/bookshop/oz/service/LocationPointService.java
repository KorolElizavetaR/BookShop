package com.bookshop.oz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bookshop.oz.dto.LocationPointDTO;
import com.bookshop.oz.dto.LocationPointDTOShops;
import com.bookshop.oz.dto.LocationStockDTO;
import com.bookshop.oz.mapper.LocationMapper;
import com.bookshop.oz.model.LocationPoint;
import com.bookshop.oz.repository.LocationPointRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationPointService {
	private final LocationPointRepository locationPointRepository;

	private final LocationMapper locationMapper;

	/**
	 * @return Магазины, доступные к выбору
	 */
	public List<LocationPointDTOShops> getShops() {
		List<LocationPoint> locationPoints = locationPointRepository.findByIsStorageFalse();
		List<LocationPointDTOShops> locationPointDTOList = locationPoints.stream()
				.map(locationMapper::getLocationPointDTOShops).collect(Collectors.toList());
		return locationPointDTOList;
	}
	
	public List<LocationPointDTO> getLocations(){
		List<LocationPoint> loc = locationPointRepository.findAll();
		List<LocationPointDTO> locations = loc.stream().map(locationMapper::getLocationPointDTO).collect(Collectors.toList());
		return locations;
	}
	
}
