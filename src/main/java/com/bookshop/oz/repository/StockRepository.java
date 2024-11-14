package com.bookshop.oz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookshop.oz.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer>{
	
}
