package com.bookshop.oz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookshop.oz.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer>{
	@Query("SELECT s FROM Stock s WHERE s.location.locationId = :locationId AND s.bookProduct.isbn = :isbn")
    Optional<Stock> findByLocationIdAndIsbn(@Param("locationId") Long locationId, @Param("isbn") String isbn);
}
