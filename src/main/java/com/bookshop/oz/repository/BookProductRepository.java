package com.bookshop.oz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookshop.oz.model.BookProduct;

@Repository
public interface BookProductRepository extends JpaRepository<BookProduct, String> {
	@Query("FROM BookProduct JOIN FETCH book")
	List<BookProduct> findAllJoin();
	long count();
	@Query("SELECT bp FROM BookProduct bp LEFT JOIN FETCH bp.book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :str, '%'))")
	List <BookProduct> findByBookNameContainingIgnoreCase(@Param("str") String str);
	
	@Query("FROM BookProduct JOIN FETCH book WHERE book.isbn = :isbn")
	Optional<BookProduct> getBookByIsbn(@Param("isbn") String isbn);
}
