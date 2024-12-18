package com.bookshop.oz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookshop.oz.model.LocationPoint;
import com.bookshop.oz.model.Order;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.model.enumeration.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query("SELECT o FROM Order o WHERE o.customer = :person AND o.status = :status")
	List<Order> findOrdersByPersonAndStatus(@Param("person") Person person, @Param("status") OrderStatus status);

	@Query("SELECT o FROM Order o WHERE o.customer = :person AND o.status = :status AND o.bookProduct.isbn = :isbn")
	Order findOrderByPersonStatusAndIsbn(@Param("person") Person person, @Param("status") OrderStatus status,
			@Param("isbn") String isbn);

	@Query("SELECT o FROM Order o WHERE o.location = :locationPoint AND o.status = :status")
	List<Order> findOrdersByLocationAndStatus(@Param("locationPoint") LocationPoint locationPoint,
			@Param("status") OrderStatus status);

	@Query("SELECT o FROM Order o JOIN FETCH o.location l WHERE o.status = :status AND l.isStorage = false ORDER BY o.orderId ASC")
	List<Order> findByStatusAndLocationIsStorageFalse(@Param("status") OrderStatus status);

	Optional<Order> findByLocationAndOrderId(LocationPoint location, Long orderId);
}
