package com.bookshop.oz.mapper;

import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.BookProductDTOItem;
import com.bookshop.oz.dto.LocationPointDTO;
import com.bookshop.oz.dto.OrderDTO;
import com.bookshop.oz.model.Order;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OrderMapper {
	private final BookProductMapper bookProductMapper;
	private final LocationMapper locationMapper;

	public OrderDTO getOrderDTO(Order order) {
		BookProductDTOItem bookProduct = bookProductMapper.getBookProductDTOItem(order.getBookProduct());
		LocationPointDTO locationPointDTO = locationMapper.getLocationPointDTO(order.getLocation());
		OrderDTO orderDTO = new OrderDTO().setBookProduct(bookProduct).setLocation(locationPointDTO)
				.setOrderId(order.getOrderId()).setQuantity(order.getQuantity()).setOrderedAt(order.getOrderedAt())
				.setArrivedAt(order.getArrivedAt());
		if (order.getClosedAt() != null) {
			orderDTO.setClosedAt(order.getClosedAt());
		}
		return orderDTO;
	}
}
