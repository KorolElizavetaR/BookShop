package com.bookshop.oz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop.oz.dto.StockDTO;
import com.bookshop.oz.mapper.StockMapper;
import com.bookshop.oz.model.Stock;
import com.bookshop.oz.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {
	@Autowired
	private final StockRepository stockRepository;
	@Autowired
	private StockMapper stockMapper;

	public List<StockDTO> getBooks() {
		List<Stock> stock = stockRepository.findAll();
		List<StockDTO> stockDTO = stock.stream().map(stockMapper::getStockDTO).collect(Collectors.toList());
		return stockDTO;
	}
}
