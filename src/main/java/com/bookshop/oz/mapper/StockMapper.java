package com.bookshop.oz.mapper;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.StockDTO;
import com.bookshop.oz.model.Stock;

@Component
public class StockMapper {
	 @Autowired
	 private ModelMapper modelMapper;
	 
	 public StockDTO getStockDTO(Stock stock) {
		 return this.modelMapper.map(stock, StockDTO.class);
	 }
	  
}
