package com.bookshop.oz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookshop.oz.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
}
