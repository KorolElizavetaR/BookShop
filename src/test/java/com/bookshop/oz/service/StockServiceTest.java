package com.bookshop.oz.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.bookshop.oz.dto.StockDTO;
import com.bookshop.oz.mapper.StockMapper;
import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.model.LocationPoint;
import com.bookshop.oz.model.Stock;
import com.bookshop.oz.repository.LocationPointRepository;
import com.bookshop.oz.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
public class StockServiceTest {
	@Autowired
	private StockService stockService;

	@MockBean
	private StockRepository stockRepository;
	@MockBean
	private LocationPointRepository locationPointRepository;
	@MockBean
	private StockMapper stockMapper;

	String isbn = "1234567891234";
	private final Short quantity = 10;
	private final String locId = "10000";

	Stock stock;
	StockDTO stockDTO;
	LocationPoint location;
	BookProduct bookProduct;

	{
		stock = new Stock().setQuantity((short) 10);
		stockDTO = new StockDTO();
		location = new LocationPoint().setLocationId(locId);
		bookProduct = new BookProduct().setIsbn(isbn);
	}

	@Test
	void testGetBooks() {
		when(stockRepository.findAll()).thenReturn(Arrays.asList(stock));
		when(stockMapper.getStockDTO(stock)).thenReturn(stockDTO);
		List<StockDTO> result = stockService.getBooks();
		assertEquals(stockDTO, result.get(0));
	}

	@Test
	void testSaveStock_Success() {
		when(locationPointRepository.findById(anyString())).thenReturn(Optional.of(location));
		when(stockRepository.findByLocationIdAndIsbn("10000", isbn)).thenReturn(Optional.empty());
		when(stockRepository.save(any(Stock.class))).thenReturn(stock);

		Stock result = stockService.saveStock(quantity, locId, bookProduct);

		assertNotNull(result);
	}

	@Test
	void testSaveStock_LocationNotFound() {
		when(locationPointRepository.findById(locId)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> stockService.saveStock(quantity, locId, bookProduct));

		verify(locationPointRepository, times(1)).findById(locId);
		verify(stockRepository, times(0)).findByLocationIdAndIsbn(anyString(), anyString());
		verify(stockRepository, times(0)).save(any(Stock.class));
	}

	@Test
	void testSaveStock_StockAlreadyExists() {
		when(locationPointRepository.findById(locId)).thenReturn(Optional.of(location));
		when(stockRepository.findByLocationIdAndIsbn(locId, isbn)).thenReturn(Optional.of(stock));

		assertThrows(RuntimeException.class, () -> stockService.saveStock(quantity, locId, bookProduct));

		verify(locationPointRepository, times(1)).findById(locId);
		verify(stockRepository, times(1)).findByLocationIdAndIsbn(locId, isbn);
		verify(stockRepository, times(0)).save(any(Stock.class));
	}
}
