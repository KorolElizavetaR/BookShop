package com.bookshop.oz.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.OrderDTOShoppingBin;
import com.bookshop.oz.model.Order;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OrderMapper {
	@Autowired
	private final ModelMapper modelMapper;
	
	public OrderDTOShoppingBin getOrderDTOShoppingBin(Order order) {
		return modelMapper.map(order, OrderDTOShoppingBin.class);
	}
}
