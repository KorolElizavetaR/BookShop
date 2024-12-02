package com.bookshop.oz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookshop.oz.model.LocationPoint;

@Repository
public interface LocationPointRepository extends JpaRepository<LocationPoint, String> {
	List <LocationPoint> findByIsStorageFalse();
}
