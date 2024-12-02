package com.bookshop.oz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookshop.oz.model.Order;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.model.enumeration.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query("SELECT o FROM Order o WHERE o.customer = :person AND o.status = :status")
	List<Order> findOrdersByPersonAndStatus(@Param("person") Person person, @Param("status") OrderStatus status);
}
