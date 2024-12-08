package com.bookshop.oz.dto;

import java.util.List;

import com.bookshop.oz.model.LocationPoint;
import com.bookshop.oz.model.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocationReport {
	private LocationPoint locationPoint;
	private List<Order> orders;
	private double totalProfit;
}
