package com.bookshop.oz.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.BookProductDTOItem;
import com.bookshop.oz.dto.LocationPointDTO;
import com.bookshop.oz.dto.OrderDTOShoppingBin;
import com.bookshop.oz.model.Order;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OrderMapper {
	@Autowired
	private final ModelMapper modelMapper;

	private final BookProductMapper bookProductMapper;
	private final LocationMapper locationMapper;

	public OrderDTOShoppingBin getOrderDTOShoppingBin(Order order) {
		BookProductDTOItem bookProduct = bookProductMapper.getBookProductDTOItem(order.getBookProduct());
		LocationPointDTO locationPointDTO = locationMapper.getLocationPointDTO(order.getLocation());
		OrderDTOShoppingBin orderDTO = new OrderDTOShoppingBin().setBookProduct(bookProduct)
				.setLocation(locationPointDTO).setOrderId(order.getOrderId()).setQuantity(order.getQuantity());
		return orderDTO;
	}
}
