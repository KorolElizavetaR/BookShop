package com.bookshop.oz.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.BookProductDTOBooksForMainPage;
import com.bookshop.oz.dto.StockDTO;
import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.model.Stock;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StockMapper {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private final LocationMapper locationMapper;

	@PostConstruct
	public void setupModelMapper() {
		modelMapper.createTypeMap(Stock.class, StockDTO.class).setPostConverter(context -> {
			Stock source = context.getSource();
			StockDTO stockDestination = context.getDestination();
			if (source.getLocation() != null) {
				stockDestination.setLocation(locationMapper.getLocationPointDTO(source.getLocation()));
			}
			return stockDestination;
		});
	}

	public StockDTO getStockDTO(Stock stock) {
		return this.modelMapper.map(stock, StockDTO.class);
	}

	public List<StockDTO> getStockListDTO(List<Stock> stockList) {
		List<StockDTO> stockListDTO = new ArrayList<>();
		for (Stock element : stockList) {
			stockListDTO.add(getStockDTO(element));
		}
		return stockListDTO;
	}
}
