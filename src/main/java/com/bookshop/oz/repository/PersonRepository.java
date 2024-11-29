package com.bookshop.oz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookshop.oz.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
	Optional<Person> findByEmail(String email);
}
