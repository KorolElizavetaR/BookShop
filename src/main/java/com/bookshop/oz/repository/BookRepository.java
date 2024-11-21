package com.bookshop.oz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookshop.oz.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String>{

}
