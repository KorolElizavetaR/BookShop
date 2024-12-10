package com.bookshop.oz.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop.oz.dto.StockDTO;
import com.bookshop.oz.mapper.StockMapper;
import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.model.LocationPoint;
import com.bookshop.oz.model.Stock;
import com.bookshop.oz.repository.LocationPointRepository;
import com.bookshop.oz.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)
public class StockService {
	private final StockRepository stockRepository;
	private final LocationPointRepository locationPointRepository;
	
	private final StockMapper stockMapper;

	public List<StockDTO> getBooks() {
		List<Stock> stock = stockRepository.findAll();
		List<StockDTO> stockDTO = stock.stream().map(stockMapper::getStockDTO).collect(Collectors.toList());
		return stockDTO;
	}
	
	@Transactional (readOnly = false)
	public Stock saveStock(Short quantity, String locId, BookProduct bookProduct) {
		LocationPoint location = locationPointRepository.findById(locId).orElseThrow();
		Optional<Stock> st = stockRepository.findByLocationIdAndIsbn(locId, bookProduct.getIsbn());
		if (st.isPresent()) throw new RuntimeException("Книга уже есть в списке");
		Stock stock = new Stock();
		stock.setLocation(location);
		stock.setBookProduct(bookProduct);
		stock.setQuantity(quantity);
		return stockRepository.save(stock);
	}
	
	public List<Stock> getStockByLocation(String locId){
		return stockRepository.findByLocation(locId);
	}
	
	@Transactional (readOnly = false)
	public void updateStockQuantity(String locId, String isbn, Short quantity) {
		Optional<Stock> st = stockRepository.findByLocationIdAndIsbn(locId, isbn);
		st.get().setQuantity(quantity);
		stockRepository.save(st.get());
	}
	
}
