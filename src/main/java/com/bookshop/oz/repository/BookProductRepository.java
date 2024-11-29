package com.bookshop.oz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookshop.oz.model.Book;
import com.bookshop.oz.model.BookProduct;

@Repository
public interface BookProductRepository extends JpaRepository<BookProduct, String> {
	@Query("FROM BookProduct JOIN FETCH book")
	List<BookProduct> findAllJoin();
//	List <BookProduct> findByBookNameContainingIgnoreCase(String str);
	long count();
}
