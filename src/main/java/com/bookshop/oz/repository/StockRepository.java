package com.bookshop.oz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookshop.oz.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
	@Query("SELECT s FROM Stock s WHERE s.location.locationId = :locationId AND s.bookProduct.isbn = :isbn")
	Optional<Stock> findByLocationIdAndIsbn(@Param("locationId") String locationId, @Param("isbn") String isbn);
	
	@Query("SELECT s FROM Stock s WHERE s.location.locationId = :locationId")
	List<Stock> findByLocation(@Param("locationId") String locationId);

	@Query("SELECT s FROM Stock s WHERE s.bookProduct.isbn = :isbn AND s.location.isStorage = true")
	Optional<Stock> findByIsbnAndIsStorageTrue(@Param("isbn") String isbn);
}
